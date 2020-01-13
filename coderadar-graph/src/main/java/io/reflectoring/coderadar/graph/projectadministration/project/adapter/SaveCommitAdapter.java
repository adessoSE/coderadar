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

      IdentityHashMap<File, FileEntity> walkedFiles = new IdentityHashMap<>(commits.size());
      List<CommitEntity> commitEntities = mapCommitTree(commits, walkedFiles);
      List<FileEntity> fileEntities = new ArrayList<>(walkedFiles.values());

      commitRepository.save(commitEntities, 0);
      fileRepository.save(fileEntities, 0);

      attachCommitsAndFilesToProject(projectId, commitEntities, fileEntities);
      saveCommitParentsRelationships(commitEntities);
      saveFileToCommitRelationShips(commitEntities);
      saveFileRenameRelationships(fileEntities);
    }
  }

  private void attachCommitsAndFilesToProject(
      Long projectId, List<CommitEntity> commitEntities, List<FileEntity> fileEntities) {
    List<Long> fileIds = new ArrayList<>(fileEntities.size());
    fileEntities.forEach(fileEntity -> fileIds.add(fileEntity.getId()));
    projectRepository.attachFilesWithIds(projectId, fileIds);

    List<Long> commitIds = new ArrayList<>(commitEntities.size());
    commitEntities.forEach(commitEntity -> commitIds.add(commitEntity.getId()));
    projectRepository.attachCommitsWithIds(projectId, commitIds);
  }

  private void saveCommitParentsRelationships(List<CommitEntity> commitEntities) {
    List<HashMap<String, Object>> parentRels = new ArrayList<>(commitEntities.size());
    for (CommitEntity commitEntity : commitEntities) {
      for (CommitEntity parent : commitEntity.getParents()) {
        HashMap<String, Object> parents = new HashMap<>(4);
        parents.put("id1", commitEntity.getId());
        parents.put("id2", parent.getId());
        parentRels.add(parents);
      }
    }
    commitRepository.createParentRelationships(parentRels);
  }

  /**
   * Sets the [:RENAMED_FROM] relationship between file nodes
   * @param fileEntities All of the (already saved) file entities.
   */
  private void saveFileRenameRelationships(List<FileEntity> fileEntities) {
    List<HashMap<String, Object>> renameRels = new ArrayList<>(10000);
    for (FileEntity fileEntity : fileEntities) {
      for (FileEntity oldFile : fileEntity.getOldFiles()) {
        HashMap<String, Object> rename = new HashMap<>(4);
        rename.put("fileId1", fileEntity.getId());
        rename.put("fileId2", oldFile.getId());
        renameRels.add(rename);
      }
      if (renameRels.size() > 10000) {
        fileRepository.createRenameRelationships(renameRels);
        renameRels.clear();
      }
    }
    fileRepository.createRenameRelationships(renameRels);
  }

  /**
   *
   * @param commitEntities
   */
  private void saveFileToCommitRelationShips(List<CommitEntity> commitEntities) {
    List<HashMap<String, Object>> fileRels = new ArrayList<>(30000);
    for (CommitEntity commitEntity : commitEntities) {
      for (FileToCommitRelationshipEntity fileToCommitRelationship :
          commitEntity.getTouchedFiles()) {
        HashMap<String, Object> files = new HashMap<>(8);
        files.put("commitId", commitEntity.getId());
        files.put("fileId", fileToCommitRelationship.getFile().getId());
        files.put("changeType", fileToCommitRelationship.getChangeType());
        files.put("oldPath", fileToCommitRelationship.getOldPath());
        fileRels.add(files);
      }
      if (fileRels.size() > 30000) {
        commitRepository.createFileRelationships(fileRels);
        fileRels.clear();
      }
    }
    commitRepository.createFileRelationships(fileRels);
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
    IdentityHashMap<Commit, CommitEntity> walkedCommits = new IdentityHashMap<>(commits.size());
    List<CommitEntity> result = new ArrayList<>(commits.size());
    for (Commit commit : commits) {
      CommitEntity commitEntity = walkedCommits.get(commit);
      if (commitEntity == null) {
        commitEntity = CommitBaseDataMapper.mapCommit(commit);
      }
      if (!commit.getParents().isEmpty()) {
        List<CommitEntity> parents = new ArrayList<>(commit.getParents().size());
        for (Commit parent : commit.getParents()) {
          CommitEntity parentCommit = walkedCommits.get(parent);
          if (parentCommit == null) {
            parentCommit = CommitBaseDataMapper.mapCommit(parent);
            walkedCommits.put(parent, parentCommit);
            result.add(parentCommit);
          }
          parents.add(parentCommit);
        }
        commitEntity.setParents(parents);
      }
      walkedCommits.put(commit, commitEntity);
      result.add(commitEntity);
      getFiles(commit.getTouchedFiles(), commitEntity, walkedFiles);
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

    List<FileToCommitRelationshipEntity> rels = new ArrayList<>(relationships.size());
    for (FileToCommitRelationship fileToCommitRelationship : relationships) {
      FileEntity fileEntity = walkedFiles.get(fileToCommitRelationship.getFile());
      if (fileEntity == null) {
        fileEntity = FileBaseDataMapper.mapFile(fileToCommitRelationship.getFile());
        walkedFiles.put(fileToCommitRelationship.getFile(), fileEntity);
        List<FileEntity> oldFiles =
            new ArrayList<>(fileToCommitRelationship.getFile().getOldFiles().size());
        for (File oldFile : fileToCommitRelationship.getFile().getOldFiles()) {
          oldFiles.add(walkedFiles.get(oldFile));
        }
        fileEntity.setOldFiles(oldFiles);
      }
      FileToCommitRelationshipEntity fileToCommitRelationshipEntity =
          new FileToCommitRelationshipEntity();
      fileToCommitRelationshipEntity.setChangeType(fileToCommitRelationship.getChangeType());
      fileToCommitRelationshipEntity.setCommit(commitEntity);
      fileToCommitRelationshipEntity.setFile(fileEntity);
      fileToCommitRelationshipEntity.setOldPath(fileToCommitRelationship.getOldPath());
      rels.add(fileToCommitRelationshipEntity);
    }
    commitEntity.setTouchedFiles(rels);
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
    projectRepository.save(projectEntity, 1);
    commitRepository.save(walkedCommits.values(), 1);
    fileRepository.save(allFiles, 1);
  }

  @Override
  public void setCommitsWithIDsAsAnalyzed(long[] commitIds) {
    commitRepository.setCommitsWithIDsAsAnalyzed(commitIds);
  }
}
