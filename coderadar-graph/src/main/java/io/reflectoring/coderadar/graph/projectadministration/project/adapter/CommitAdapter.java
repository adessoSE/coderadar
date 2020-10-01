package io.reflectoring.coderadar.graph.projectadministration.project.adapter;

import com.google.common.collect.Maps;
import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.FileRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.MetricRepository;
import io.reflectoring.coderadar.graph.projectadministration.ForceUpdateChecker;
import io.reflectoring.coderadar.graph.projectadministration.branch.repository.BranchRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.FileEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.Branch;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.File;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.UpdateCommitsPort;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommitAdapter implements SaveCommitPort, UpdateCommitsPort {
  private final ProjectRepository projectRepository;
  private final FileRepository fileRepository;
  private final CommitRepository commitRepository;
  private final BranchRepository branchRepository;
  private final MetricRepository metricRepository;
  private final ForceUpdateChecker forceUpdateChecker;

  private final CommitBaseDataMapper commitBaseDataMapper = new CommitBaseDataMapper();
  private final FileBaseDataMapper fileBaseDataMapper = new FileBaseDataMapper();

  @Override
  public void saveCommits(List<Commit> commits, List<Branch> branches, long projectId) {
    if (!commits.isEmpty()) {
      IdentityHashMap<File, FileEntity> walkedFiles = new IdentityHashMap<>(commits.size() * 2);
      Collection<CommitEntity> commitEntities = mapCommitTree(commits, walkedFiles);
      saveCommitAndFileEntities(projectId, commitEntities, walkedFiles.values(), branches);
    }
  }

  private void setBranchPointers(long projectId, List<Branch> branches) {
    for (Branch branch : branches) {
      if (!branchRepository.branchExistsInProject(projectId, branch.getName())) {
        branchRepository.setBranchOnCommit(
            projectId, branch.getCommitHash(), branch.getName(), branch.isTag());
      } else {
        branchRepository.moveBranchToCommit(projectId, branch.getName(), branch.getCommitHash());
      }
    }
  }

  private void saveFilesWithDepthZero(Collection<FileEntity> fileEntities, int fileBulkSaveChunk) {
    List<FileEntity> tempFileList =
        new ArrayList<>(Math.min(fileBulkSaveChunk, fileEntities.size()));
    for (FileEntity fileEntity : fileEntities) {
      tempFileList.add(fileEntity);
      if (tempFileList.size() == fileBulkSaveChunk) {
        fileRepository.save(tempFileList, 0);
        tempFileList.clear();
      }
    }
    fileRepository.save(tempFileList, 0);
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
      long projectId,
      Collection<CommitEntity> commitEntities,
      Collection<FileEntity> fileEntities,
      int commitBulkSaveChunk,
      int fileBulkSaveChunk) {
    List<Long> ids =
        new ArrayList<>(
            Math.min(Math.max(commitEntities.size(), fileEntities.size()), fileBulkSaveChunk));
    for (FileEntity fileEntity : fileEntities) {
      ids.add(fileEntity.getId());
      if (ids.size() == fileBulkSaveChunk) {
        projectRepository.attachFilesWithIds(projectId, ids);
        ids.clear();
      }
    }
    projectRepository.attachFilesWithIds(projectId, ids);
    // reuse a portion of the above list to avoid new allocation
    ids.clear();
    for (CommitEntity commitEntity : commitEntities) {
      ids.add(commitEntity.getId());
      if (ids.size() == commitBulkSaveChunk) {
        projectRepository.attachCommitsWithIds(projectId, ids);
        ids.clear();
      }
    }
    projectRepository.attachCommitsWithIds(projectId, ids);
  }

  /**
   * Sets the [:IS_CHILD_OF] relationship between commit nodes
   *
   * @param commitEntities All of the (already saved) commit entities.
   * @param saveChunk The amount of commit parent relationships to save at once.
   */
  private void saveCommitParentsRelationships(
      Collection<CommitEntity> commitEntities, int saveChunk) {

    List<Long[]> parentRels = new ArrayList<>(saveChunk);

    // Instead of creating lots of arrays in the loops below, we just
    // fill the list now and reuse the arrays later.
    for (int i = 0; i < saveChunk; i++) {
      parentRels.add(new Long[] {0L, 0L, 0L});
    }

    int i = 0;
    for (CommitEntity commitEntity : commitEntities) {
      long j = 0;
      for (CommitEntity parent : commitEntity.getParents()) {
        parentRels.get(i)[0] = commitEntity.getId();
        parentRels.get(i)[1] = parent.getId();
        parentRels.get(i++)[2] = commitEntity.getParents().size() == 1 ? null : j++;
        if (i == saveChunk) {
          commitRepository.createParentRelationships(parentRels);
          i = 0;
        }
      }
    }
    commitRepository.createParentRelationships(parentRels.subList(0, i));
  }

  /**
   * Sets the [:RENAMED_FROM] relationship between file nodes
   *
   * @param fileEntities All of the (already saved) file entities.
   * @param arrayBuffer An arrayList containing arrays of 3 long elements each.
   */
  private void saveFileRenameRelationships(
      Collection<FileEntity> fileEntities, List<long[]> arrayBuffer) {
    int i = 0;
    int saveChunk = arrayBuffer.size();
    for (FileEntity fileEntity : fileEntities) {
      for (FileEntity oldFile : fileEntity.getOldFiles()) {
        arrayBuffer.get(i)[0] = fileEntity.getId();
        arrayBuffer.get(i++)[1] = oldFile.getId();
        if (i == saveChunk) {
          fileRepository.createRenameRelationships(arrayBuffer);
          i = 0;
        }
      }
    }
    fileRepository.createRenameRelationships(arrayBuffer.subList(0, i));
  }

  /**
   * Sets the [:CHANGED_IN] and [:DELETED_IN] relationships between file and commit nodes
   *
   * @param commitEntities All of the (already saved) commit entities.
   * @param arrayBuffer An arrayList containing arrays of 3 long elements each.
   */
  private void saveFileToCommitRelationships(
      Collection<CommitEntity> commitEntities, List<long[]> arrayBuffer) {
    // Save the changed files (everything except deletes)
    int i = 0;
    int saveChunk = arrayBuffer.size();
    for (CommitEntity commitEntity : commitEntities) {
      for (FileEntity fileEntity : commitEntity.getChangedFiles()) {
        arrayBuffer.get(i)[0] = commitEntity.getId();
        arrayBuffer.get(i++)[1] = fileEntity.getId();
        if (i == saveChunk) {
          commitRepository.createFileRelationships(arrayBuffer);
          i = 0;
        }
      }
    }
    commitRepository.createFileRelationships(arrayBuffer.subList(0, i));

    // Save DELETED_IN relationships
    i = 0;
    for (CommitEntity commitEntity : commitEntities) {
      for (FileEntity fileEntity : commitEntity.getDeletedFiles()) {
        arrayBuffer.get(i)[0] = commitEntity.getId();
        arrayBuffer.get(i++)[1] = fileEntity.getId();
        if (i == saveChunk) {
          commitRepository.createFileDeleteRelationships(arrayBuffer);
          i = 0;
        }
      }
    }
    commitRepository.createFileDeleteRelationships(arrayBuffer.subList(0, i));
  }

  /**
   * Creates CommitEntities with initialized parents.
   *
   * @param commits A list of domain Commit objects that need to be mapped to CommitEntities
   * @param walkedFiles A map of files that have been processed already
   * @return All CommitEntities in the project.
   */
  private Collection<CommitEntity> mapCommitTree(
      List<Commit> commits, IdentityHashMap<File, FileEntity> walkedFiles) {
    IdentityHashMap<Commit, CommitEntity> walkedCommits = new IdentityHashMap<>(commits.size());
    for (Commit commit : commits) {
      CommitEntity commitEntity =
          walkedCommits.computeIfAbsent(commit, commitBaseDataMapper::mapDomainObject);
      if (!commit.getParents().isEmpty()) {
        List<CommitEntity> parents = new ArrayList<>(commit.getParents().size());
        for (Commit parent : commit.getParents()) {
          CommitEntity parentCommit =
              walkedCommits.computeIfAbsent(parent, commitBaseDataMapper::mapDomainObject);
          parents.add(parentCommit);
        }
        commitEntity.setParents(parents);
      }
      commitEntity.setChangedFiles(getFileEntities(commit.getChangedFiles(), walkedFiles));
      commitEntity.setDeletedFiles(getFileEntities(commit.getDeletedFiles(), walkedFiles));
    }
    return walkedCommits.values();
  }

  /**
   * Maps FileToCommitRelationships to FileToCommitRelationshipEntities.
   *
   * @param files The files that we have to map to DB entities.
   * @param walkedFiles Files we have already walked. We need this to prevent endless recursion.
   * @return A list of FileToCommitRelationshipEntity objects with initialized FileEntity fields.
   */
  private List<FileEntity> getFileEntities(
      List<File> files, IdentityHashMap<File, FileEntity> walkedFiles) {

    List<FileEntity> rels = new ArrayList<>(files.size());
    for (File file : files) {
      FileEntity fileEntity =
          walkedFiles.computeIfAbsent(
              file,
              f -> {
                var entity = fileBaseDataMapper.mapDomainObject(f);
                List<FileEntity> oldFiles = new ArrayList<>(f.getOldFiles().size());
                for (File oldFile : f.getOldFiles()) {
                  oldFiles.add(walkedFiles.get(oldFile));
                }
                entity.setOldFiles(oldFiles);
                return entity;
              });
      rels.add(fileEntity);
    }
    return rels;
  }

  private void addCommits(long projectId, List<Commit> commits, List<Branch> updatedBranches) {

    // Get all of the existing commits and save them in a map
    Map<String, CommitEntity> walkedCommits = Maps.newHashMapWithExpectedSize(commits.size());
    Map<String, FileEntity> walkedFiles = new HashMap<>();
    commitRepository.findByProjectId(projectId).forEach(c -> walkedCommits.put(c.getHash(), c));

    // Remove any already existing commits from the new commits passed into this method
    commits.removeIf(commit -> walkedCommits.containsKey(commit.getHash()));
    commits.sort(Comparator.comparingLong(Commit::getTimestamp));

    // We'll save the newly added commits in this list
    List<CommitEntity> newCommitEntities = new ArrayList<>(commits.size());

    for (Commit commit : commits) {
      CommitEntity commitEntity = commitBaseDataMapper.mapDomainObject(commit);
      List<CommitEntity> parents = new ArrayList<>(commit.getParents().size());
      commit.getParents().forEach(parent -> parents.add(walkedCommits.get(parent.getHash())));
      commitEntity.setParents(parents);
      walkedCommits.put(commitEntity.getHash(), commitEntity);

      List<FileEntity> changedFiles = new ArrayList<>(commit.getChangedFiles().size());
      List<FileEntity> deletedFiles = new ArrayList<>(commit.getDeletedFiles().size());

      for (File file : commit.getChangedFiles()) {
        FileEntity fileEntity = fileRepository.getFileInProjectByPath(projectId, file.getPath());
        if (fileEntity == null) {
          fileEntity =
              walkedFiles.computeIfAbsent(
                  file.getPath(),
                  s -> {
                    var entity = fileBaseDataMapper.mapDomainObject(file);
                    entity.setOldFiles(new ArrayList<>(file.getOldFiles().size()));
                    for (File oldFile : file.getOldFiles()) {
                      FileEntity oldFileEntity =
                          fileRepository.getFileInProjectByPath(projectId, oldFile.getPath());
                      if (oldFileEntity == null) {
                        oldFileEntity = walkedFiles.get(oldFile.getPath());
                      }
                      entity.getOldFiles().add(oldFileEntity);
                    }
                    return entity;
                  });
        }
        changedFiles.add(fileEntity);
      }

      for (File file : commit.getDeletedFiles()) {
        FileEntity fileEntity = fileRepository.getFileInProjectByPath(projectId, file.getPath());
        if (fileEntity == null) {
          fileEntity =
              walkedFiles.computeIfAbsent(
                  file.getPath(), s -> fileBaseDataMapper.mapDomainObject(file));
        }
        deletedFiles.add(fileEntity);
      }
      commitEntity.setDeletedFiles(deletedFiles);
      commitEntity.setChangedFiles(changedFiles);
      newCommitEntities.add(commitEntity);
    }
    saveCommitAndFileEntities(projectId, newCommitEntities, walkedFiles.values(), updatedBranches);
  }

  private void saveCommitAndFileEntities(
      long projectId,
      Collection<CommitEntity> commitEntities,
      Collection<FileEntity> fileEntities,
      List<Branch> branches) {
    int commitBulkSaveChunk = 5000;
    if (commitEntities.size() < 5000) {
      commitBulkSaveChunk = commitEntities.size();
    }

    int fileBulkSaveChunk = 5000;

    commitRepository.save(commitEntities, 0);
    saveFilesWithDepthZero(fileEntities, fileBulkSaveChunk);
    attachCommitsAndFilesToProject(
        projectId, commitEntities, fileEntities, commitBulkSaveChunk, fileBulkSaveChunk);
    setBranchPointers(projectId, branches);

    // Parent rels = amount of commits * 1.5
    saveCommitParentsRelationships(commitEntities, commitBulkSaveChunk / 2 + commitBulkSaveChunk);

    // Instead of creating lots of arrays in the loops below, we just
    // fill the list now and reuse the arrays later.
    List<long[]> arrayBuffer = new ArrayList<>(fileBulkSaveChunk);
    for (int i = 0; i < fileBulkSaveChunk; i++) {
      arrayBuffer.add(new long[] {0L, 0L});
    }

    saveFileToCommitRelationships(commitEntities, arrayBuffer);
    saveFileRenameRelationships(fileEntities, arrayBuffer);
  }

  @Override
  public void setCommitsWithIDsAsAnalyzed(List<Long> commitIds) {
    commitRepository.setCommitsWithIDsAsAnalyzed(commitIds);
  }

  @Override
  public void updateCommits(long projectId, List<Commit> commits, List<Branch> updatedBranches) {
    Set<String> newCommitHashes = new HashSet<>(commits.size());
    commits.forEach(c -> newCommitHashes.add(c.getHash()));

    List<CommitEntity> unreachableCommits = Collections.emptyList();
    for (Branch branch : updatedBranches) {
      if (branchRepository.branchExistsInProject(projectId, branch.getName())) {
        unreachableCommits = forceUpdateChecker.getUnreachableCommits(projectId, newCommitHashes);
        break;
      }
    }
    if (!unreachableCommits.isEmpty()) {
      deleteCommits(projectId, updatedBranches, unreachableCommits);
    }
    addCommits(projectId, commits, updatedBranches);
  }

  private void deleteCommits(
      long projectId, List<Branch> updatedBranches, List<CommitEntity> unreachableCommits) {
    unreachableCommits.forEach(
        commitEntity -> {
          metricRepository.deleteMetricsForCommit(commitEntity.getId());
          commitRepository.deleteCommitAndAddedOrRenamedFiles(commitEntity.getId());
        });
    setBranchPointers(projectId, updatedBranches);
  }
}
