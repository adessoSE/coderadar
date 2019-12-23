package io.reflectoring.coderadar.graph.query.adapter;

import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.FileToCommitRelationshipEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.adapter.CommitBaseDataMapper;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.File;
import io.reflectoring.coderadar.projectadministration.domain.FileToCommitRelationship;
import io.reflectoring.coderadar.query.port.driven.GetCommitsInProjectPort;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class GetCommitsInProjectAdapter implements GetCommitsInProjectPort {
  private final ProjectRepository projectRepository;
  private final CommitRepository commitRepository;

  public GetCommitsInProjectAdapter(
      ProjectRepository projectRepository, CommitRepository commitRepository) {
    this.projectRepository = projectRepository;
    this.commitRepository = commitRepository;
  }

  private List<Commit> mapCommitEntities(List<CommitEntity> commitEntities) {
    HashMap<Long, Commit> walkedCommits = new HashMap<>();
    HashMap<Long, File> walkedFiles = new HashMap<>();
    List<Commit> result = new ArrayList<>();
    for (CommitEntity commitEntity : commitEntities) {
      Commit commit = walkedCommits.get(commitEntity.getId());
      if (commit == null) {
        commit = CommitBaseDataMapper.mapCommitEntity(commitEntity);
      }
      for (CommitEntity parent : commitEntity.getParents()) {
        Commit parentCommit = walkedCommits.get(parent.getId());
        if (parentCommit == null) {
          parentCommit = CommitBaseDataMapper.mapCommitEntity(parent);
          parentCommit.setTouchedFiles(
              getFiles(parent.getTouchedFiles(), parentCommit, walkedFiles));
          result.add(parentCommit);
        }
        commit.getParents().add(parentCommit);
      }
      commit.setTouchedFiles(getFiles(commitEntity.getTouchedFiles(), commit, walkedFiles));
      walkedCommits.put(commitEntity.getId(), commit);
      result.add(commit);
    }
    return result;
  }

  @Override
  public List<Commit> getCommitsSortedByTimestampDescWithNoRelationships(Long projectId) {
    if (!projectRepository.existsById(projectId)) {
      throw new ProjectNotFoundException(projectId);
    }
    return commitRepository
        .findByProjectIdAndTimestampDesc(projectId)
        .stream()
        .map(
            commitEntity -> {
              Commit commit = new Commit();
              commit.setName(commitEntity.getName());
              commit.setAnalyzed(commitEntity.isAnalyzed());
              commit.setAuthor(commitEntity.getAuthor());
              commit.setComment(commitEntity.getComment());
              commit.setTimestamp(commitEntity.getTimestamp());
              return commit;
            })
        .collect(Collectors.toList());
  }

  @Override
  public List<Commit> getSortedByTimestampAsc(Long projectId) {
    if (!projectRepository.existsById(projectId)) {
      throw new ProjectNotFoundException(projectId);
    }
    List<CommitEntity> commitEntities =
        commitRepository.findByProjectIdWithAllRelationshipsSortedByTimestampAsc(projectId);
    return mapCommitEntities(commitEntities);
  }

  @Override
  public List<Commit> getSortedByTimestampAscWithNoParents(Long projectId) {
    if (!projectRepository.existsById(projectId)) {
      throw new ProjectNotFoundException(projectId);
    }
    List<CommitEntity> commitEntities =
        commitRepository.findByProjectIdWithFileRelationshipsSortedByTimestampAsc(projectId);
    return mapCommitEntitiesNoParents(commitEntities);
  }

  @Override
  public List<Commit> getNonanalyzedSortedByTimestampAscWithNoParents(Long projectId) {
    if (!projectRepository.existsById(projectId)) {
      throw new ProjectNotFoundException(projectId);
    }
    List<CommitEntity> commitEntities =
        commitRepository.findByProjectIdNonanalyzedWithFileRelationshipsSortedByTimestampAsc(
            projectId);
    return mapCommitEntitiesNoParents(commitEntities);
  }

  private List<Commit> mapCommitEntitiesNoParents(List<CommitEntity> commitEntities) {
    List<Commit> commits = new ArrayList<>();
    HashMap<Long, File> walkedFiles = new HashMap<>();
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
    List<FileToCommitRelationship> fileToCommitRelationships = new ArrayList<>();

    for (FileToCommitRelationshipEntity fileToCommitRelationshipEntity : relationships) {
      FileToCommitRelationship fileToCommitRelationship = new FileToCommitRelationship();
      fileToCommitRelationship.setId(fileToCommitRelationshipEntity.getId());
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
      file.getCommits().add(fileToCommitRelationship);
      fileToCommitRelationship.setFile(file);

      fileToCommitRelationships.add(fileToCommitRelationship);
    }
    return fileToCommitRelationships;
  }
}
