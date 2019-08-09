package io.reflectoring.coderadar.graph.query.service;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.analyzer.domain.File;
import io.reflectoring.coderadar.analyzer.domain.FileToCommitRelationship;
import io.reflectoring.coderadar.graph.analyzer.domain.CommitEntity;
import io.reflectoring.coderadar.graph.analyzer.domain.FileToCommitRelationshipEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.graph.query.repository.GetCommitsInProjectRepository;
import io.reflectoring.coderadar.projectadministration.CommitNotFoundException;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.query.port.driven.GetCommitsInProjectPort;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetCommitsInProjectAdapter implements GetCommitsInProjectPort {
  private final GetProjectRepository getProjectRepository;
  private final GetCommitsInProjectRepository getCommitsInProjectRepository;

  @Autowired
  public GetCommitsInProjectAdapter(
      GetProjectRepository getProjectRepository,
      GetCommitsInProjectRepository getCommitsInProjectRepository) {
    this.getProjectRepository = getProjectRepository;
    this.getCommitsInProjectRepository = getCommitsInProjectRepository;
  }

  @Override
  public List<Commit> get(Long projectId) {
    Optional<ProjectEntity> persistedProject = getProjectRepository.findById(projectId);

    if (persistedProject.isPresent()) {
      List<Commit> commits = new ArrayList<>();
      List<CommitEntity> commitEntities = getCommitsInProjectRepository.findByProjectId(projectId);
      for (CommitEntity commitEntity1 : commitEntities) {
        CommitEntity commitEntity =
            getCommitsInProjectRepository
                .findById(commitEntity1.getId())
                .orElseThrow(() -> new CommitNotFoundException(commitEntity1.getId()));

        Commit commit = new Commit();
        commit.setId(commitEntity.getId());
        commit.setName(commitEntity.getName());
        commit.setTimestamp(commitEntity.getTimestamp());
        commit.setAuthor(commitEntity.getAuthor());
        commit.setComment(commitEntity.getComment());
        commit.setMerged(commitEntity.isMerged());
        commit.setAnalyzed(commitEntity.isAnalyzed());
        commit.setParents(findAndSaveParents(commitEntity, new HashMap<>(), new HashMap<>()));
        commit.setTouchedFiles(getFiles(commitEntity.getTouchedFiles(), commit, new HashMap<>()));
        commits.add(commit);
      }
      commits.sort((o1, o2) -> o2.getTimestamp().compareTo(o1.getTimestamp()));
      return commits;
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }

  private List<Commit> findAndSaveParents(
      CommitEntity commitEntity,
      HashMap<String, Commit> walkedCommits,
      HashMap<String, File> walkedFiles) {

    List<Commit> parents = new ArrayList<>();

    for (CommitEntity c : commitEntity.getParents()) {

      Commit commit = walkedCommits.get(c.getName());
      if (commit != null) {
        parents.add(commit);
      } else {
        commit = new Commit();
        commit.setId(c.getId());
        commit.setName(c.getName());
        commit.setAuthor(c.getAuthor());
        commit.setComment(c.getComment());
        commit.setTimestamp(c.getTimestamp());
        commit.setTouchedFiles(getFiles(c.getTouchedFiles(), commit, walkedFiles));
        walkedCommits.put(c.getName(), commit);
        commit.setParents(findAndSaveParents(c, walkedCommits, walkedFiles));
        parents.add(commit);
      }
    }
    return parents;
  }

  private List<FileToCommitRelationship> getFiles(
      List<FileToCommitRelationshipEntity> relationships,
      Commit entity,
      HashMap<String, File> walkedFiles) {
    List<FileToCommitRelationship> fileToCommitRelationships = new ArrayList<>();

    for (FileToCommitRelationshipEntity fileToCommitRelationshipEntity : relationships) {
      FileToCommitRelationship fileToCommitRelationship = new FileToCommitRelationship();
      fileToCommitRelationship.setId(fileToCommitRelationshipEntity.getId());
      fileToCommitRelationship.setCommit(entity);
      fileToCommitRelationship.setChangeType(fileToCommitRelationshipEntity.getChangeType());
      fileToCommitRelationship.setOldPath(fileToCommitRelationshipEntity.getOldPath());

      File file = walkedFiles.get(fileToCommitRelationshipEntity.getFile().getPath());
      if (file == null) {
        file = new File();
        file.setId(fileToCommitRelationshipEntity.getFile().getId());
        file.setPath(fileToCommitRelationshipEntity.getFile().getPath());
        walkedFiles.put(file.getPath(), file);
      }
      file.getCommits().add(fileToCommitRelationship);
      fileToCommitRelationship.setFile(file);

      fileToCommitRelationships.add(fileToCommitRelationship);
    }
    return fileToCommitRelationships;
  }
}
