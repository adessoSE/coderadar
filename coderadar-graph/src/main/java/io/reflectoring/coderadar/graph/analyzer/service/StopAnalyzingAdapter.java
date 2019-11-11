package io.reflectoring.coderadar.graph.analyzer.service;

import io.reflectoring.coderadar.analyzer.AnalyzingJobNotStartedException;
import io.reflectoring.coderadar.analyzer.port.driven.StopAnalyzingPort;
import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzingJobEntity;
import io.reflectoring.coderadar.graph.analyzer.repository.AnalyzingJobRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class StopAnalyzingAdapter implements StopAnalyzingPort {
  private final AnalyzingJobRepository analyzingJobRepository;
  private final ProjectRepository projectRepository;

  public StopAnalyzingAdapter(
      AnalyzingJobRepository analyzingJobRepository, ProjectRepository projectRepository) {
    this.analyzingJobRepository = analyzingJobRepository;
    this.projectRepository = projectRepository;
  }

  @Override
  public void stop(Long projectId) {
    if (!projectRepository.existsById(projectId)) {
      throw new ProjectNotFoundException(projectId);
    }

    Optional<AnalyzingJobEntity> persistedAnalyzingJob =
        analyzingJobRepository.findByProjectId(projectId);

    if (persistedAnalyzingJob.isPresent()) {
      AnalyzingJobEntity analyzingJob = persistedAnalyzingJob.get();

      if (!analyzingJob.isActive()) {
        throw new AnalyzingJobNotStartedException("Can't stop a non-running analyzing job.");
      } else {
        analyzingJob.setActive(false);
        analyzingJobRepository.save(analyzingJob);
      }
    }
  }
}
