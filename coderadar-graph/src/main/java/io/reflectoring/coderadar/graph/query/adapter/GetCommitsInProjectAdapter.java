package io.reflectoring.coderadar.graph.query.adapter;

import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.FileToCommitRelationshipEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.adapter.CommitBaseDataMapper;
import io.reflectoring.coderadar.projectadministration.domain.*;
import io.reflectoring.coderadar.query.port.driven.GetCommitsInProjectPort;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetCommitsInProjectAdapter implements GetCommitsInProjectPort {
  private final CommitRepository commitRepository;

  public GetCommitsInProjectAdapter(CommitRepository commitRepository) {
    this.commitRepository = commitRepository;
  }

  private List<Commit> mapCommitEntities(List<CommitEntity> commitEntities) {
    HashMap<Long, Commit> walkedCommits = new HashMap<>((int) (commitEntities.size() / 0.75) + 1);
    HashMap<Long, File> walkedFiles = new HashMap<>((int) (commitEntities.size() / 0.75) + 1);
    List<Commit> result = new ArrayList<>(commitEntities.size());
    for (CommitEntity commitEntity : commitEntities) {
      Commit commit = walkedCommits.get(commitEntity.getId());
      if (commit == null) {
        commit = CommitBaseDataMapper.mapCommitEntity(commitEntity);
      }
      List<Commit> parents = new ArrayList<>(commitEntity.getParents().size());
      for (CommitEntity parent : commitEntity.getParents()) {
        Commit parentCommit = walkedCommits.get(parent.getId());
        if (parentCommit == null) {
          parentCommit = CommitBaseDataMapper.mapCommitEntity(parent);
          parentCommit.setTouchedFiles(
              getFiles(parent.getTouchedFiles(), parentCommit, walkedFiles));
          result.add(parentCommit);
        }
        parents.add(parentCommit);
      }
      commit.setParents(parents);
      commit.setTouchedFiles(getFiles(commitEntity.getTouchedFiles(), commit, walkedFiles));
      walkedCommits.put(commitEntity.getId(), commit);
      result.add(commit);
    }
    return result;
  }

  @Override
  public List<Commit> getCommitsSortedByTimestampDescWithNoRelationships(Long projectId) {
    List<CommitEntity> commitEntities = commitRepository.findByProjectIdAndTimestampDesc(projectId);
    List<Commit> commits = new ArrayList<>(commitEntities.size());
    for (CommitEntity commitEntity : commitEntities) {
      commits.add(CommitBaseDataMapper.mapCommitEntity(commitEntity));
    }
    return commits;
  }

  @Override
  public List<Commit> getSortedByTimestampAsc(Long projectId) {
    List<CommitEntity> commitEntities =
        commitRepository.findByProjectIdWithAllRelationshipsSortedByTimestampAsc(projectId);
    return mapCommitEntities(commitEntities);
  }

  /**
   * Returns all not yet analyzed commits in this project, that match the supplied file patterns
   *
   * @param projectId The id of the project.
   * @param filePatterns The patterns to use.
   * @return A list of commits with initialized FileToCommitRelationShips and no parents.
   */
  @Override
  public List<Commit> getNonAnalyzedSortedByTimestampAscWithNoParents(
      Long projectId, List<FilePattern> filePatterns) {
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
        commitRepository.findByProjectIdNonAnalyzedWithFileRelationshipsSortedByTimestampAsc(
            projectId, includes, excludes);

    return mapCommitEntitiesNoParents(commitEntities);
  }

  private List<Commit> mapCommitEntitiesNoParents(List<CommitEntity> commitEntities) {
    List<Commit> commits = new ArrayList<>(commitEntities.size());
    HashMap<Long, File> walkedFiles = new HashMap<>((int) (commitEntities.size() / 0.75) + 1);
    for (CommitEntity commitEntity : commitEntities) {
      Commit commit = CommitBaseDataMapper.mapCommitEntity(commitEntity);
      commit.setTouchedFiles(getFiles(commitEntity.getTouchedFiles(), commit, walkedFiles));
      commits.add(commit);
    }
    return commits;
  }

  private List<FileToCommitRelationship> getFiles(
      List<FileToCommitRelationshipEntity> relationships,
      Commit entity,
      HashMap<Long, File> walkedFiles) {
    List<FileToCommitRelationship> fileToCommitRelationships =
        new ArrayList<>(relationships.size());

    for (FileToCommitRelationshipEntity fileToCommitRelationshipEntity : relationships) {
      FileToCommitRelationship fileToCommitRelationship = new FileToCommitRelationship();
      fileToCommitRelationship.setCommit(entity);
      fileToCommitRelationship.setOldPath(fileToCommitRelationshipEntity.getOldPath());
      fileToCommitRelationship.setChangeType(fileToCommitRelationshipEntity.getChangeType());

      File file = walkedFiles.get(fileToCommitRelationshipEntity.getFile().getId());
      if (file == null) {
        file = new File();
        file.setId(fileToCommitRelationshipEntity.getFile().getId());
        file.setPath(fileToCommitRelationshipEntity.getFile().getPath());
        walkedFiles.put(file.getId(), file);
      }
      fileToCommitRelationship.setFile(file);

      fileToCommitRelationships.add(fileToCommitRelationship);
    }
    return fileToCommitRelationships;
  }
}
