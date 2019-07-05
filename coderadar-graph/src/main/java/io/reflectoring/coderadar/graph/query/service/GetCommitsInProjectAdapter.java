package io.reflectoring.coderadar.graph.query.service;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.graph.analyzer.domain.CommitEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.graph.query.repository.GetCommitsInProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.query.port.driven.GetCommitsInProjectPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
      for(CommitEntity commitEntity : getCommitsInProjectRepository.findByProjectId(projectId)){
        Commit commit = new Commit();
        commit.setId(commitEntity.getId());
        commit.setName(commitEntity.getName());
        commit.setTimestamp(commitEntity.getTimestamp());
        commit.setAuthor(commitEntity.getAuthor());
        commit.setComment(commitEntity.getComment());
        commit.setMerged(commitEntity.isMerged());
        commit.setAnalyzed(commitEntity.isAnalyzed());
        commits.add(commit);
      }
      commits.sort((o1, o2) -> o2.getTimestamp().compareTo(o1.getTimestamp()));
      return commits;
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }

  @Override
  public Commit findTop1ByProjectIdOrderBySequenceNumberDesc(Long id) {
    return getCommitsInProjectRepository.findTop1ByProjectIdOrderBySequenceNumberDesc(id);
  }
}
