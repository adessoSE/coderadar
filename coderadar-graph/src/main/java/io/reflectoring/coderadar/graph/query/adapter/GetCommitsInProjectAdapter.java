package io.reflectoring.coderadar.graph.query.adapter;

import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.projectadministration.branch.repository.BranchRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.FileEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.FileToCommitRelationshipEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.adapter.CommitBaseDataMapper;
import io.reflectoring.coderadar.projectadministration.domain.*;
import io.reflectoring.coderadar.query.port.driven.GetCommitsInProjectPort;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class GetCommitsInProjectAdapter implements GetCommitsInProjectPort {
  private final CommitRepository commitRepository;
  private final BranchRepository branchRepository;
  private final CommitBaseDataMapper commitBaseDataMapper = new CommitBaseDataMapper();

  public GetCommitsInProjectAdapter(
      CommitRepository commitRepository, BranchRepository branchRepository) {
    this.commitRepository = commitRepository;
    this.branchRepository = branchRepository;
  }

  @Override
  public List<Commit> getCommitsSortedByTimestampDescWithNoRelationships(
      Long projectId, String branch) {
    List<CommitEntity> commitsWithParents =
        commitRepository.findByProjectIdWithParentRelationships(projectId);

    CommitEntity branchCommit = branchRepository.getCommitForBranch(projectId, branch);
    if (branchCommit == null) {
      return new ArrayList<>();
    }

    List<CommitEntity> startCommitList =
        commitsWithParents.stream()
            .filter(commitEntity -> commitEntity.getName().equals(branchCommit.getName()))
            .collect(Collectors.toList());

    CommitEntity startCommit;
    if (startCommitList.isEmpty()) {
      return new ArrayList<>();
    } else {
      startCommit = startCommitList.get(0);
    }
    List<CommitEntity> result = new ArrayList<>();
    walkCommitsByParents(startCommit, result);
    result.sort((t1, t2) -> -Long.compare(t1.getTimestamp(), t2.getTimestamp()));
    List<Commit> domainObjects = new ArrayList<>();
    for (CommitEntity commitEntity : result) {
      domainObjects.add(commitBaseDataMapper.mapNodeEntity(commitEntity));
    }
    return domainObjects;
  }

  @Override
  public List<Commit> getNonAnalyzedSortedByTimestampAscWithNoParents(
      Long projectId, List<FilePattern> filePatterns, String branch) {
    // Map Ant-Patterns to RegEx
    List<String> includes = new ArrayList<>();
    List<String> excludes = new ArrayList<>();
    for (FilePattern filePattern : filePatterns) {
      if (filePattern.getInclusionType().equals(InclusionType.INCLUDE)) {
        includes.add(PatternUtil.toPattern(filePattern.getPattern()).toString());
      } else {
        excludes.add(PatternUtil.toPattern(filePattern.getPattern()).toString());
      }
    }

    List<CommitEntity> commitEntities =
        commitRepository.findByProjectIdNonAnalyzedWithFileAndParentRelationships(
            projectId, includes, excludes);

    CommitEntity branchCommit = branchRepository.getCommitForBranch(projectId, branch);
    CommitEntity startCommit =
        commitEntities.stream()
            .filter(commitEntity -> commitEntity.getId().equals(branchCommit.getId()))
            .collect(Collectors.toList())
            .get(0);

    List<CommitEntity> result = new ArrayList<>();
    walkCommitsByParents(startCommit, result);
    result.sort(Comparator.comparingLong(CommitEntity::getTimestamp));
    return mapCommitEntitiesNoParents(result);
  }

  /**
   * Maps a list of commit entities to Commit domain objects. Does not map parent relationships.
   *
   * @param commitEntities The commit entities to map.
   * @return A list of commit domain objects.
   */
  private List<Commit> mapCommitEntitiesNoParents(List<CommitEntity> commitEntities) {
    List<Commit> commits = new ArrayList<>(commitEntities.size());
    IdentityHashMap<FileEntity, File> walkedFiles = new IdentityHashMap<>(commitEntities.size());
    for (CommitEntity commitEntity : commitEntities) {
      Commit commit = commitBaseDataMapper.mapNodeEntity(commitEntity);
      commit.setTouchedFiles(
          mapFileToCommitRelationships(commitEntity.getTouchedFiles(), walkedFiles));
      commits.add(commit);
    }
    return commits;
  }

  /**
   * Maps FileToCommitRelationshipEntities to FileToCommitRelationships. Creates a File domain
   * object for each FileEntity
   *
   * @param touchedFiles The files touched in the current commit.
   * @param walkedFiles A map of all the file entities created so far. (Empty on first method run)
   * @return A list of FileToCommitRelationships with the accompanying files.
   */
  private List<FileToCommitRelationship> mapFileToCommitRelationships(
      List<FileToCommitRelationshipEntity> touchedFiles,
      IdentityHashMap<FileEntity, File> walkedFiles) {
    List<FileToCommitRelationship> fileToCommitRelationships = new ArrayList<>(touchedFiles.size());

    for (FileToCommitRelationshipEntity fileToCommitRelationshipEntity : touchedFiles) {
      FileToCommitRelationship fileToCommitRelationship = new FileToCommitRelationship();

      File file = walkedFiles.get(fileToCommitRelationshipEntity.getFile());
      if (file == null) {
        file = new File();
        file.setId(fileToCommitRelationshipEntity.getFile().getId());
        file.setPath(fileToCommitRelationshipEntity.getFile().getPath());
        walkedFiles.put(fileToCommitRelationshipEntity.getFile(), file);
      }
      fileToCommitRelationship.setFile(file);

      fileToCommitRelationships.add(fileToCommitRelationship);
    }
    return fileToCommitRelationships;
  }

  /**
   * Recursively adds commits to a result list until a commit with no parents is reached.
   *
   * @param startCommit The commit to start at.
   * @param result A list to add the resulting commits to.
   */
  private void walkCommitsByParents(CommitEntity startCommit, List<CommitEntity> result) {
    result.add(startCommit);
    if (!startCommit.getParents().isEmpty()) {
      for (CommitEntity parent : startCommit.getParents()) {
        if (!result.contains(parent)) {
          walkCommitsByParents(parent, result);
        }
      }
    }
  }
}
