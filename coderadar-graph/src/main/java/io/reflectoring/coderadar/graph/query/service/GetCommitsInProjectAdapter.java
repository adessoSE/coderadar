package io.reflectoring.coderadar.graph.query.service;

import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.FileToCommitRelationshipEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.service.CommitBaseDataMapper;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.File;
import io.reflectoring.coderadar.projectadministration.domain.FileToCommitRelationship;
import io.reflectoring.coderadar.query.port.driven.GetCommitsInProjectPort;
import io.reflectoring.coderadar.query.port.driver.GetCommitResponse;
import java.util.*;
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
    for (CommitEntity commitEntity : commitEntities) {
      Commit commit = walkedCommits.get(commitEntity.getId());
      if (commit == null) {
        commit = CommitBaseDataMapper.mapCommitEntity(commitEntity);
      }
      for (CommitEntity parent : commitEntity.getParents()) {
        Commit parentCommit = walkedCommits.get(parent.getId());
        if (parentCommit == null) {
          parentCommit = CommitBaseDataMapper.mapCommitEntity(parent);
        }
        commit.getParents().add(parentCommit);
      }
      commit.setTouchedFiles(getFiles(commitEntity.getTouchedFiles(), commit, walkedFiles));
      walkedCommits.put(commitEntity.getId(), commit);
    }
    return new ArrayList<>(walkedCommits.values());
  }

  @Override
  public List<GetCommitResponse> getCommitsResponseSortedByTimestampDesc(Long projectId) {
    Optional<ProjectEntity> persistedProject = projectRepository.findById(projectId);
    if (persistedProject.isPresent()) {
      List<CommitEntity> commitEntities =
          commitRepository.findByProjectIdAndTimestampDesc(projectId);
      List<GetCommitResponse> getCommitResponses = new ArrayList<>();
      for (CommitEntity c : commitEntities) {
        GetCommitResponse commitResponse = new GetCommitResponse();
        commitResponse.setName(c.getName());
        commitResponse.setAnalyzed(c.isAnalyzed());
        commitResponse.setAuthor(c.getAuthor());
        commitResponse.setComment(c.getComment());
        commitResponse.setTimestamp(c.getTimestamp().getTime());
        getCommitResponses.add(commitResponse);
      }
      return getCommitResponses;
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }

  @Override
  public List<Commit> getSortedByTimestampAsc(Long projectId) {
    Optional<ProjectEntity> persistedProject = projectRepository.findById(projectId);
    if (persistedProject.isPresent()) {
      List<CommitEntity> commitEntities =
          commitRepository.findByProjectIdWithAllRelationshipsSortedByTimestampAsc(projectId);
      List<Commit> commits = mapCommitEntities(commitEntities);
      commits.sort(Comparator.comparing(Commit::getTimestamp));
      return commits;
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }

  @Override
  public List<Commit> getSortedByTimestampAscWithNoParents(Long projectId) {
    Optional<ProjectEntity> persistedProject = projectRepository.findById(projectId);
    if (persistedProject.isPresent()) {
      List<CommitEntity> commitEntities =
          commitRepository.findByProjectIdWithFileRelationshipsSortedByTimestampAsc(projectId);
      return mapCommitEntitiesNoParents(commitEntities);
    } else {
      throw new ProjectNotFoundException(projectId);
    }
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
