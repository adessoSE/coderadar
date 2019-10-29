package io.reflectoring.coderadar.graph.analyzer.service;

import com.google.common.collect.Iterables;
import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.analyzer.domain.FileToCommitRelationship;
import io.reflectoring.coderadar.graph.analyzer.domain.CommitEntity;
import io.reflectoring.coderadar.graph.analyzer.domain.FileEntity;
import io.reflectoring.coderadar.graph.analyzer.domain.FileToCommitRelationshipEntity;
import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.FileRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.plugin.api.ChangeType;
import io.reflectoring.coderadar.projectadministration.CommitNotFoundException;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveCommitAdapter implements SaveCommitPort {
  private final CommitRepository commitRepository;
  private final ProjectRepository projectRepository;
  private final FileRepository fileRepository;

  @Autowired
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

      Commit newestCommit = commits.get(commits.size() - 1);
      CommitEntity commitEntity = mapCommitBaseData(newestCommit, newestCommit.getId());

      commitEntity.setParents(findAndSaveParents(newestCommit, walkedCommits));
      walkedCommits.putIfAbsent(commitEntity.getName(), commitEntity);
      List<CommitEntity> commitEntities = new ArrayList<>(walkedCommits.values());

      commitEntities.sort(Comparator.comparing(CommitEntity::getTimestamp));
      for (int i = 0; i < commitEntities.size(); i++) {
        getFiles(commits.get(i).getTouchedFiles(), commitEntities.get(i), walkedFiles);
      }

      List<FileEntity> allFiles = new ArrayList<>();
      for (List<FileEntity> files : walkedFiles.values()) {
        allFiles.addAll(files);
      }

      projectEntity.getFiles().addAll(allFiles);

      commitRepository.save(walkedCommits.values(), 1);
      fileRepository.save(allFiles, 1);

      projectEntity.getCommits().addAll(walkedCommits.values());
      projectRepository.save(projectEntity, 1);
    }
  }
  /**
   * Maps a Commit object to a CommitEntity object. Does not set files or parents
   *
   * @param commit The commit to map.
   * @return A new CommitEntity object.
   */
  private CommitEntity mapCommitBaseData(Commit commit, Long commitId) {
    CommitEntity commitEntity;
    if (commitId == null) {
      commitEntity = new CommitEntity();
    } else {
      commitEntity =
          commitRepository
              .findById(commitId)
              .orElseThrow(() -> new CommitNotFoundException(commitId));
    }
    commitEntity.setAnalyzed(commit.isAnalyzed());
    commitEntity.setAuthor(commit.getAuthor());
    commitEntity.setComment(commit.getComment());
    commitEntity.setMerged(commit.isMerged());
    commitEntity.setName(commit.getName());
    commitEntity.setTimestamp(commit.getTimestamp());
    return commitEntity;
  }

  /**
   * Saves a single commit in the database , this operation can only be used to save/update basic
   * data (no parents/files).
   *
   * @param commit The commit to save/update.
   */
  @Override
  public void saveCommit(Commit commit) {
    CommitEntity commitEntity = mapCommitBaseData(commit, commit.getId());
    commitRepository.save(commitEntity, 0);
  }

  @Override
  public void setCommitsWithIDsAsAnalyzed(List<Long> commitIds) {
    commitRepository.setCommitsWithIDsAsAnalyzed(commitIds);
  }

  /**
   * Recursively finds all parents of a Commit, and returns a new CommitEntity tree. Also calls
   * getFiles to set the files and relationships.
   *
   * @param commit The commit whose parents to find.
   * @param walkedCommits Commits we have already found/created. We need this to prevent creating
   *     thousands of duplicate nodes and going into endless recursion
   * @return A list of fully initialized parents for the given commit.
   */
  private List<CommitEntity> findAndSaveParents(
      Commit commit, Map<String, CommitEntity> walkedCommits) {
    List<CommitEntity> parents = new ArrayList<>();
    for (Commit c : commit.getParents()) {
      CommitEntity commitEntity = walkedCommits.get(c.getName());
      if (commitEntity != null) {
        parents.add(commitEntity);
      } else {
        commitEntity = new CommitEntity();
        walkedCommits.putIfAbsent(c.getName(), commitEntity);

        commitEntity.setName(c.getName());
        commitEntity.setAuthor(c.getAuthor());
        commitEntity.setComment(c.getComment());
        commitEntity.setTimestamp(c.getTimestamp());
        commitEntity.setParents(findAndSaveParents(c, walkedCommits));
        parents.add(commitEntity);
      }
    }
    return parents;
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
  private void getFiles(
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
          file.getOldFiles().addAll(walkedFiles.get(fileToCommitRelationship.getOldPath()));
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
}
