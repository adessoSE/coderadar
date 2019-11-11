package io.reflectoring.coderadar.graph.analyzer.service;

import com.google.common.collect.Iterables;
import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.FileRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.FileEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.FileToCommitRelationshipEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.plugin.api.ChangeType;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.FileToCommitRelationship;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.AddCommitsPort;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class SaveCommitAdapter implements SaveCommitPort, AddCommitsPort {
  private final CommitRepository commitRepository;
  private final ProjectRepository projectRepository;
  private final FileRepository fileRepository;

  public SaveCommitAdapter(
      CommitRepository commitRepository,
      ProjectRepository projectRepository,
      FileRepository fileRepository) {
    this.commitRepository = commitRepository;
    this.projectRepository = projectRepository;
    this.fileRepository = fileRepository;
  }

  /**
   * This method should be used for the initial creation of Commits when saving a project. It maps
   * all of the domain objects to entities and saves them in the DB.
   *
   * @param commits The commit tree to save.
   * @param projectId The id of the project.
   */
  @Override
  public void saveCommits(List<Commit> commits, Long projectId) {
    if (!commits.isEmpty()) {
      ProjectEntity projectEntity =
          projectRepository
              .findById(projectId)
              .orElseThrow(() -> new ProjectNotFoundException(projectId));
      HashMap<String, List<FileEntity>> walkedFiles = new HashMap<>();
      HashMap<String, CommitEntity> walkedCommits = new HashMap<>();

      commits.sort(Comparator.comparing(Commit::getTimestamp));

      for (Commit commit : commits) {
        walkedCommits.put(commit.getName(), CommitBaseDataMapper.mapCommit(commit));
      }

      for (Commit commit : commits) {
        CommitEntity commitEntity = walkedCommits.get(commit.getName());
        // set parents
        for (Commit parent : commit.getParents()) {
          commitEntity.getParents().add(walkedCommits.get(parent.getName()));
        }
        walkedCommits.putIfAbsent(commitEntity.getName(), commitEntity);
      }

      List<CommitEntity> commitEntities = new ArrayList<>(walkedCommits.values());

      commitEntities.sort(Comparator.comparing(CommitEntity::getTimestamp));
      for (int i = 0; i < commitEntities.size(); i++) {
        getFiles(commits.get(i).getTouchedFiles(), commitEntities.get(i), walkedFiles);
      }

      List<FileEntity> allFiles = new ArrayList<>();
      for (List<FileEntity> files : walkedFiles.values()) {
        allFiles.addAll(files);
      }

      projectEntity.setFiles(allFiles);

      commitRepository.save(walkedCommits.values(), 1);
      fileRepository.save(allFiles, 1);

      projectEntity.getCommits().addAll(walkedCommits.values());
      projectRepository.save(projectEntity, 1);
    }
  }

  @Override
  public void setCommitsWithIDsAsAnalyzed(List<Long> commitIds) {
    commitRepository.setCommitsWithIDsAsAnalyzed(commitIds);
  }

  /**
   * Sets the files and relationships for a given CommitEntity
   *
   * @param relationships The relationships that we have to map to DB entities.
   * @param entity The commitEntity
   * @param walkedFiles Files we have already walked. We need this to prevent endless recursion when
   *     mapping.
   */
  // TODO: Fix this mess
  static void getFiles(
      List<FileToCommitRelationship> relationships,
      CommitEntity entity,
      Map<String, List<FileEntity>> walkedFiles) {
    for (FileToCommitRelationship fileToCommitRelationship : relationships) {

      List<FileEntity> fileEntities = new ArrayList<>();
      List<FileEntity> fileList = walkedFiles.get(fileToCommitRelationship.getFile().getPath());

      if (fileList == null) {
        FileEntity fileEntity = new FileEntity();
        if (fileToCommitRelationship.getChangeType().equals(ChangeType.RENAME)) {
          List<FileEntity> filesWithOldPath =
              walkedFiles.get(fileToCommitRelationship.getOldPath());
          if (filesWithOldPath != null) {
            fileEntity.getOldFiles().addAll(filesWithOldPath);
          }
        }
        fileEntities.add(fileEntity);
        fileList = new ArrayList<>(fileEntities);

      } else {
        if ((fileToCommitRelationship.getChangeType().equals(ChangeType.ADD))) {
          FileEntity fileEntity = new FileEntity();
          fileEntities.add(fileEntity);
          fileList.add(fileEntity);
        } else if ((fileToCommitRelationship.getChangeType().equals(ChangeType.DELETE))) {
          fileEntities.addAll(fileList);
        } else if (fileToCommitRelationship.getChangeType().equals(ChangeType.RENAME)) {
          FileEntity file = new FileEntity();
          List<FileEntity> filesWithOldPath =
              walkedFiles.get(fileToCommitRelationship.getOldPath());
          if (filesWithOldPath != null) {
            file.getOldFiles().addAll(filesWithOldPath);
          }
          fileEntities.add(file);
          fileList.add(file);
        } else {
          fileEntities.add(Iterables.getLast(fileList));
        }
      }

      for (FileEntity fileEntity : fileEntities) {

        FileToCommitRelationshipEntity fileToCommitRelationshipEntity =
            new FileToCommitRelationshipEntity();
        fileToCommitRelationshipEntity.setCommit(entity);
        fileToCommitRelationshipEntity.setChangeType(fileToCommitRelationship.getChangeType());
        fileToCommitRelationshipEntity.setOldPath(fileToCommitRelationship.getOldPath());

        fileToCommitRelationshipEntity.setFile(fileEntity);
        fileEntity.setPath(fileToCommitRelationship.getFile().getPath());
        if (fileEntity
            .getCommits()
            .stream()
            .noneMatch(
                fileToCommitRelationship1 ->
                    fileToCommitRelationship1.getCommit().getName().equals(entity.getName()))) {
          fileEntity.getCommits().add(fileToCommitRelationshipEntity);
        }
        if (!walkedFiles.containsKey(fileEntity.getPath())) {
          walkedFiles.put(fileEntity.getPath(), fileList);
        } else {
          walkedFiles.replace(fileEntity.getPath(), fileList);
        }
      }
    }
  }

  /**
   * Adds new commits to an existing project.
   *
   * @param commits The new commits to add.
   * @param projectId The project id.
   */
  public void addCommits(List<Commit> commits, Long projectId) {
    ProjectEntity projectEntity =
        projectRepository
            .findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId));
    Map<String, CommitEntity> walkedCommits = new HashMap<>();
    for (CommitEntity c : commitRepository.findByProjectId(projectId)) {
      walkedCommits.put(c.getName(), c);
    }
    Map<String, List<FileEntity>> walkedFiles = new HashMap<>();
    for (FileEntity f : fileRepository.findAllinProject(projectId)) {
      walkedFiles.computeIfAbsent(f.getPath(), k -> new ArrayList<>());
      walkedFiles.get(f.getPath()).add(f);
    }

    for (Commit commit : commits) {
      walkedCommits.put(commit.getName(), CommitBaseDataMapper.mapCommit(commit));
    }

    for (Commit commit : commits) {
      CommitEntity commitEntity = walkedCommits.get(commit.getName());
      // set parents
      for (Commit parent : commit.getParents()) {
        commitEntity.getParents().add(walkedCommits.get(parent.getName()));
      }
      // set files
      SaveCommitAdapter.getFiles(commit.getTouchedFiles(), commitEntity, walkedFiles);
      walkedCommits.put(commit.getName(), commitEntity);
      projectEntity.getCommits().add(commitEntity);
    }
    List<FileEntity> allFiles = new ArrayList<>();
    for (List<FileEntity> files : walkedFiles.values()) {
      allFiles.addAll(files);
    }

    commitRepository.save(walkedCommits.values(), 1);
    fileRepository.save(allFiles, 1);
    projectEntity.setFiles(allFiles);
    projectEntity.getCommits().addAll(walkedCommits.values());
    projectRepository.save(projectEntity, 1);
  }
}
