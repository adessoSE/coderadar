package io.reflectoring.coderadar.graph.projectadministration.project.adapter;

import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.FileRepository;
import io.reflectoring.coderadar.graph.projectadministration.branch.repository.BranchRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.FileEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.FileToCommitRelationshipEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Branch;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.File;
import io.reflectoring.coderadar.projectadministration.domain.FileToCommitRelationship;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.AddCommitsPort;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class CommitAdapter implements SaveCommitPort, AddCommitsPort {
  private final ProjectRepository projectRepository;
  private final FileRepository fileRepository;
  private final CommitRepository commitRepository;
  private final BranchRepository branchRepository;
  private final CommitBaseDataMapper commitBaseDataMapper = new CommitBaseDataMapper();
  private final FileBaseDataMapper fileBaseDataMapper = new FileBaseDataMapper();

  public CommitAdapter(
      CommitRepository commitRepository,
      ProjectRepository projectRepository,
      FileRepository fileRepository,
      BranchRepository branchRepository) {
    this.projectRepository = projectRepository;
    this.fileRepository = fileRepository;
    this.commitRepository = commitRepository;
    this.branchRepository = branchRepository;
  }

  @Override
  public void saveCommits(List<Commit> commits, List<Branch> branches, Long projectId) {
    if (!commits.isEmpty()) {
      IdentityHashMap<File, FileEntity> walkedFiles = new IdentityHashMap<>(commits.size() * 2);
      List<CommitEntity> commitEntities = mapCommitTree(commits, walkedFiles);
      List<FileEntity> fileEntities = new ArrayList<>(walkedFiles.values());

      commits.clear();
      walkedFiles.clear();

      int commitBulkSaveChunk = 5000;
      if (commitEntities.size() < 5000) {
        commitBulkSaveChunk = commitEntities.size();
      }

      int fileBulkSaveChunk = 5000;
      if (fileEntities.size() < 5000) {
        fileBulkSaveChunk = fileEntities.size();
      }

      saveCommitsWithDepthZero(commitEntities, commitBulkSaveChunk);
      saveFilesWithDepthZero(fileEntities, fileBulkSaveChunk);
      attachCommitsAndFilesToProject(
          projectId, commitEntities, fileEntities, commitBulkSaveChunk, fileBulkSaveChunk);
      saveCommitParentsRelationships(commitEntities, commitBulkSaveChunk);
      saveFileToCommitRelationships(commitEntities, fileBulkSaveChunk);
      saveFileRenameRelationships(fileEntities, fileBulkSaveChunk);
      setBranchPointers(projectId, branches);
    }
  }

  private void setBranchPointers(Long projectId, List<Branch> branches) {
    for (Branch branch : branches) {
      branchRepository.setBranchOnCommit(projectId, branch.getCommitHash(), branch.getName());
    }
  }

  private void saveFilesWithDepthZero(List<FileEntity> fileEntities, int fileBulkSaveChunk) {
    List<FileEntity> tempFileList = new ArrayList<>(fileBulkSaveChunk);
    for (FileEntity fileEntity : fileEntities) {
      tempFileList.add(fileEntity);
      if (tempFileList.size() == fileBulkSaveChunk) {
        fileRepository.save(tempFileList, 0);
        tempFileList.clear();
      }
    }
    fileRepository.save(tempFileList, 0);
  }

  private void saveCommitsWithDepthZero(
      List<CommitEntity> commitEntities, int commitBulkSaveChunk) {
    List<CommitEntity> tempCommitList = new ArrayList<>(commitBulkSaveChunk);
    for (CommitEntity commitEntity : commitEntities) {
      tempCommitList.add(commitEntity);
      if (tempCommitList.size() == commitBulkSaveChunk) {
        commitRepository.save(tempCommitList, 0);
        tempCommitList.clear();
      }
    }
    commitRepository.save(tempCommitList, 0);
  }

  /**
   * Sets the [:CONTAINS] AND [:CONTAINS_COMMIT] relationships between file/commit nodes and the
   * project
   *
   * @param commitEntities All of the (already saved) commit entities.
   * @param fileEntities All of the (already saved) file entities.
   * @param commitBulkSaveChunk The amount of commits to save at once.
   * @param fileBulkSaveChunk The amount of files to save at once.
   */
  private void attachCommitsAndFilesToProject(
      Long projectId,
      List<CommitEntity> commitEntities,
      List<FileEntity> fileEntities,
      int commitBulkSaveChunk,
      int fileBulkSaveChunk) {
    List<Long> fileIds = new ArrayList<>(fileBulkSaveChunk);
    for (FileEntity fileEntity : fileEntities) {
      fileIds.add(fileEntity.getId());
      if (fileIds.size() == fileBulkSaveChunk) {
        projectRepository.attachFilesWithIds(projectId, fileIds);
        fileIds.clear();
      }
    }
    projectRepository.attachFilesWithIds(projectId, fileIds);

    List<Long> commitIds = new ArrayList<>(commitBulkSaveChunk);
    for (CommitEntity commitEntity : commitEntities) {
      commitIds.add(commitEntity.getId());
      if (commitIds.size() == commitBulkSaveChunk) {
        projectRepository.attachCommitsWithIds(projectId, commitIds);
        commitIds.clear();
      }
    }
    projectRepository.attachCommitsWithIds(projectId, commitIds);
  }

  /**
   * Sets the [:IS_CHILD_OF] relationship between commit nodes
   *
   * @param commitEntities All of the (already saved) commit entities.
   * @param commitBulkSaveChunk The amount of commit parent relationships to save at once.
   */
  private void saveCommitParentsRelationships(
      List<CommitEntity> commitEntities, int commitBulkSaveChunk) {
    commitBulkSaveChunk = commitBulkSaveChunk / 2 + commitBulkSaveChunk;
    List<HashMap<String, Object>> parentRels = new ArrayList<>(commitBulkSaveChunk);
    for (CommitEntity commitEntity : commitEntities) {
      for (CommitEntity parent : commitEntity.getParents()) {
        HashMap<String, Object> parents = new HashMap<>(4);
        parents.put("id1", commitEntity.getId());
        parents.put("id2", parent.getId());
        parentRels.add(parents);
        if (parentRels.size() == commitBulkSaveChunk) {
          commitRepository.createParentRelationships(parentRels);
          parentRels.clear();
        }
      }
    }
    commitRepository.createParentRelationships(parentRels);
  }

  /**
   * Sets the [:RENAMED_FROM] relationship between file nodes
   *
   * @param fileEntities All of the (already saved) file entities.
   * @param fileBulkSaveChunk The amount of file rename relationships to save at once.
   */
  private void saveFileRenameRelationships(List<FileEntity> fileEntities, int fileBulkSaveChunk) {
    List<HashMap<String, Object>> renameRels = new ArrayList<>(fileBulkSaveChunk);
    for (FileEntity fileEntity : fileEntities) {
      for (FileEntity oldFile : fileEntity.getOldFiles()) {
        HashMap<String, Object> rename = new HashMap<>(4);
        rename.put("fileId1", fileEntity.getId());
        rename.put("fileId2", oldFile.getId());
        renameRels.add(rename);
      }
      if (renameRels.size() == fileBulkSaveChunk) {
        fileRepository.createRenameRelationships(renameRels);
        renameRels.clear();
      }
    }
    fileRepository.createRenameRelationships(renameRels);
  }

  /**
   * Sets the [:CHANGED_IN] relationships between file and commit nodes
   *
   * @param commitEntities All of the (already saved) commit entities.
   * @param fileBulkSaveChunk The amount of file to commits relationships to save at once.
   */
  private void saveFileToCommitRelationships(
      List<CommitEntity> commitEntities, int fileBulkSaveChunk) {
    List<HashMap<String, Object>> fileRels = new ArrayList<>(fileBulkSaveChunk);
    int entitiesAmount = commitEntities.size();
    for (int i = 0; i < entitiesAmount; i++) {
      CommitEntity commitEntity = commitEntities.get(i);
      for (FileToCommitRelationshipEntity fileToCommitRelationship :
          commitEntity.getTouchedFiles()) {
        HashMap<String, Object> files = new HashMap<>(8);
        files.put("commitId", commitEntity.getId());
        files.put("fileId", fileToCommitRelationship.getFile().getId());
        files.put("changeType", fileToCommitRelationship.getChangeType());
        files.put("oldPath", fileToCommitRelationship.getOldPath());
        fileRels.add(files);
        if (fileRels.size() == fileBulkSaveChunk) {
          commitRepository.createFileRelationships(fileRels);
          fileRels.clear();
        }
        commitEntities.set(i, null);
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
        commitEntity = commitBaseDataMapper.mapDomainObject(commit);
        walkedCommits.put(commit, commitEntity);
        result.add(commitEntity);
      }
      if (!commit.getParents().isEmpty()) {
        List<CommitEntity> parents = new ArrayList<>(commit.getParents().size());
        for (Commit parent : commit.getParents()) {
          CommitEntity parentCommit = walkedCommits.get(parent);
          if (parentCommit == null) {
            parentCommit = commitBaseDataMapper.mapDomainObject(parent);
            walkedCommits.put(parent, parentCommit);
            result.add(parentCommit);
          }
          parents.add(parentCommit);
        }
        commitEntity.setParents(parents);
      }
      commitEntity.setTouchedFiles(getFiles(commit.getTouchedFiles(), commitEntity, walkedFiles));
    }
    return result;
  }

  /**
   * Maps FileToCommitRelationships to FileToCommitRelationshipEntities.
   *
   * @param relationships The relationships that we have to map to DB entities.
   * @param commitEntity The target commitEntity
   * @param walkedFiles Files we have already walked. We need this to prevent endless recursion.
   * @return A list of FileToCommitRelationshipEntity objects with initialized FileEntity fields.
   */
  private List<FileToCommitRelationshipEntity> getFiles(
      List<FileToCommitRelationship> relationships,
      CommitEntity commitEntity,
      IdentityHashMap<File, FileEntity> walkedFiles) {

    List<FileToCommitRelationshipEntity> rels = new ArrayList<>(relationships.size());
    for (FileToCommitRelationship fileToCommitRelationship : relationships) {
      FileEntity fileEntity = walkedFiles.get(fileToCommitRelationship.getFile());
      if (fileEntity == null) {
        fileEntity = fileBaseDataMapper.mapDomainObject(fileToCommitRelationship.getFile());
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
    return rels;
  }

  @Override
  public void addCommits(long projectId, List<Commit> commits, List<Branch> updatedBranches) {
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
      walkedFiles.put(fileBaseDataMapper.mapNodeEntity(f), f);
    }
    commits.removeIf(commit -> walkedCommits.containsKey(commit.getName()));

    for (Commit commit : commits) {
      walkedCommits.put(commit.getName(), commitBaseDataMapper.mapDomainObject(commit));
    }

    for (Commit commit : commits) {
      CommitEntity commitEntity = walkedCommits.get(commit.getName());
      // set parents
      for (Commit parent : commit.getParents()) {
        if (commitEntity.getParents().isEmpty()) {
          commitEntity.setParents(new ArrayList<>(parent.getParents().size()));
        }
        commitEntity.getParents().add(walkedCommits.get(parent.getName()));
      }
      // set files
      commitEntity.setTouchedFiles(getFiles(commit.getTouchedFiles(), commitEntity, walkedFiles));
      walkedCommits.put(commit.getName(), commitEntity);
    }
    List<FileEntity> allFiles = new ArrayList<>(walkedFiles.values());
    projectEntity.setFiles(allFiles);
    projectEntity.setCommits(new ArrayList<>(walkedCommits.values()));
    projectRepository.save(projectEntity, 1);
    commitRepository.save(walkedCommits.values(), 1);
    fileRepository.save(allFiles, 1);
    for (Branch branch : updatedBranches) {
      if (!branchRepository.branchExistsInProject(projectId, branch.getName())) {
        branchRepository.setBranchOnCommit(projectId, branch.getCommitHash(), branch.getName());
      } else {
        branchRepository.moveBranchToCommit(projectId, branch.getName(), branch.getCommitHash());
      }
    }
  }

  @Override
  public void setCommitsWithIDsAsAnalyzed(List<Long> commitIds) {
    commitRepository.setCommitsWithIDsAsAnalyzed(commitIds);
  }
}
