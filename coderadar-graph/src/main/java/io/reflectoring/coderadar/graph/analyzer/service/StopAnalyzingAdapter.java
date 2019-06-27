package io.reflectoring.coderadar.graph.analyzer.service;

import io.reflectoring.coderadar.analyzer.AnalyzingJobNotStartedException;
import io.reflectoring.coderadar.analyzer.port.driven.StopAnalyzingPort;
import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzingJobEntity;
import io.reflectoring.coderadar.graph.analyzer.repository.GetAnalyzingStatusRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.StartAnalyzingRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StopAnalyzingAdapter implements StopAnalyzingPort {
  private final GetAnalyzingStatusRepository getAnalyzingStatusRepository;
  private final StartAnalyzingRepository stopAnalyzingRepository;
  private final GetProjectRepository getProjectRepository;

  @Autowired
  public StopAnalyzingAdapter(
      GetAnalyzingStatusRepository getAnalyzingStatusRepository,
      StartAnalyzingRepository stopAnalyzingRepository,
      GetProjectRepository getProjectRepository) {
    this.getAnalyzingStatusRepository = getAnalyzingStatusRepository;
    this.stopAnalyzingRepository = stopAnalyzingRepository;
    this.getProjectRepository = getProjectRepository;
  }

  @Override
  public void stop(Long projectId) {
    ProjectEntity persistedProject =
        getProjectRepository
            .findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId));

    Optional<AnalyzingJobEntity> persistedAnalyzingJob =
        getAnalyzingStatusRepository.findByProjectId(projectId);

    if (persistedAnalyzingJob.isPresent()) {
      AnalyzingJobEntity analyzingJob = persistedAnalyzingJob.get();

      if (!analyzingJob.isActive()) {
        throw new AnalyzingJobNotStartedException("Can't stop a non-running analyzing job.");
      } else {
        analyzingJob.setActive(false);
        stopAnalyzingRepository.save(analyzingJob);
      }
    }
  }
}
