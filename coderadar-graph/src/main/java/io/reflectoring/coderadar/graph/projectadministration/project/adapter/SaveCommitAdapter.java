package io.reflectoring.coderadar.graph.projectadministration.project.adapter;

import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.FileRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.FileEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.FileToCommitRelationshipEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.File;
import io.reflectoring.coderadar.projectadministration.domain.FileToCommitRelationship;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.AddCommitsPort;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class SaveCommitAdapter implements SaveCommitPort, AddCommitsPort {
  private final ProjectRepository projectRepository;
  private final FileRepository fileRepository;
  private final CommitRepository commitRepository;

  public SaveCommitAdapter(
      CommitRepository commitRepository,
      ProjectRepository projectRepository,
      FileRepository fileRepository) {
    this.projectRepository = projectRepository;
    this.fileRepository = fileRepository;
    this.commitRepository = commitRepository;
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

      IdentityHashMap<File, FileEntity> walkedFiles = new IdentityHashMap<>();
      List<CommitEntity> commitEntities = mapCommitTree(commits, walkedFiles);
      List<FileEntity> fileEntities = new ArrayList<>(walkedFiles.values());

      projectEntity.setFiles(fileEntities);
      projectEntity.setCommits(commitEntities);
      projectRepository.save(projectEntity, 1);
    }
  }

  /**
   * Creates CommitEntities with initialized parents.
   *
   * @param commits A list of domain Commit objects that need to be mapped to CommitEntities
   * @param walkedFiles A map of files that have been processed already
   * @return All CommitEntities in the project.
   */
  private List<CommitEntity> mapCommitTree(
      List<Commit> commits, IdentityHashMap<File, FileEntity> walkedFiles) {
    IdentityHashMap<Commit, CommitEntity> walkedCommits = new IdentityHashMap<>();
    List<CommitEntity> result = new ArrayList<>();
    for (Commit commit : commits) {
      CommitEntity commitEntity = walkedCommits.get(commit);
      if (commitEntity == null) {
        commitEntity = CommitBaseDataMapper.mapCommit(commit);
      }
      for (Commit parent : commit.getParents()) {
        CommitEntity parentCommit = walkedCommits.get(parent);
        if (parentCommit == null) {
          parentCommit = CommitBaseDataMapper.mapCommit(parent);
          walkedCommits.put(parent, parentCommit);
          result.add(parentCommit);
        }
        commitEntity.getParents().add(parentCommit);
      }
      walkedCommits.put(commit, commitEntity);
      result.add(commitEntity);
      getFiles(commit.getTouchedFiles(), commitEntity, walkedFiles);
      commitRepository.save(commitEntity, 1);
    }
    return result;
  }

  /**
   * Sets the files and relationships for a given CommitEntity
   *
   * @param relationships The relationships that we have to map to DB entities.
   * @param commitEntity The commitEntity
   * @param walkedFiles Files we have already walked. We need this to prevent endless recursion when
   */
  private void getFiles(
      List<FileToCommitRelationship> relationships,
      CommitEntity commitEntity,
      IdentityHashMap<File, FileEntity> walkedFiles) {

    for (FileToCommitRelationship fileToCommitRelationship : relationships) {
      FileEntity fileEntity = walkedFiles.get(fileToCommitRelationship.getFile());
      if (fileEntity == null) {
        fileEntity = FileBaseDataMapper.mapFile(fileToCommitRelationship.getFile());
        walkedFiles.put(fileToCommitRelationship.getFile(), fileEntity);
        for (File oldFile : fileToCommitRelationship.getFile().getOldFiles()) {
          fileEntity.getOldFiles().add(walkedFiles.get(oldFile));
        }
        fileRepository.save(fileEntity, 1);
      }
      FileToCommitRelationshipEntity fileToCommitRelationshipEntity =
          new FileToCommitRelationshipEntity();
      fileToCommitRelationshipEntity.setChangeType(fileToCommitRelationship.getChangeType());
      fileToCommitRelationshipEntity.setCommit(commitEntity);
      fileToCommitRelationshipEntity.setFile(fileEntity);
      fileToCommitRelationshipEntity.setOldPath(fileToCommitRelationship.getOldPath());
      commitEntity.getTouchedFiles().add(fileToCommitRelationshipEntity);
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
    IdentityHashMap<File, FileEntity> walkedFiles = new IdentityHashMap<>();
    for (FileEntity f : fileRepository.findAllinProject(projectId)) {
      walkedFiles.put(FileBaseDataMapper.mapFileEntity(f), f);
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
      getFiles(commit.getTouchedFiles(), commitEntity, walkedFiles);
      walkedCommits.put(commit.getName(), commitEntity);
      projectEntity.getCommits().add(commitEntity);
    }
    List<FileEntity> allFiles = new ArrayList<>(walkedFiles.values());
    projectEntity.setFiles(allFiles);
    projectEntity.setCommits(new ArrayList<>(walkedCommits.values()));
    projectRepository.save(projectEntity, 2);
  }

  @Override
  public void setCommitsWithIDsAsAnalyzed(long[] commitIds) {
    commitRepository.setCommitsWithIDsAsAnalyzed(commitIds);
  }
}
