package io.reflectoring.coderadar.graph.analyzer.service;

import io.reflectoring.coderadar.analyzer.AnalyzingJobNotStartedException;
import io.reflectoring.coderadar.analyzer.domain.AnalyzingJob;
import io.reflectoring.coderadar.analyzer.port.driven.StopAnalyzingPort;
import io.reflectoring.coderadar.graph.analyzer.repository.GetAnalyzingStatusRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.StopAnalyzingRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StopAnalyzingAdapter implements StopAnalyzingPort {
  private final GetAnalyzingStatusRepository getAnalyzingStatusRepository;
  private final StopAnalyzingRepository stopAnalyzingRepository;
  private final GetProjectRepository getProjectRepository;

  @Autowired
  public StopAnalyzingAdapter(
      GetAnalyzingStatusRepository getAnalyzingStatusRepository,
      StopAnalyzingRepository stopAnalyzingRepository,
      GetProjectRepository getProjectRepository) {
    this.getAnalyzingStatusRepository = getAnalyzingStatusRepository;
    this.stopAnalyzingRepository = stopAnalyzingRepository;
    this.getProjectRepository = getProjectRepository;
  }

  @Override
  public void stop(Long projectId) {
    Optional<Project> persistedProject = getProjectRepository.findById(projectId);

    if (persistedProject.isPresent()) {
      Optional<AnalyzingJob> persistedAnalyzingJob =
          getAnalyzingStatusRepository.findByProject_Id(projectId);

      if (persistedAnalyzingJob.isPresent()) {
        AnalyzingJob analyzingJob = persistedAnalyzingJob.get();

        if (!analyzingJob.isActive()) {
          throw new AnalyzingJobNotStartedException("Can't stop a non-running analyzing job.");
        } else {
          analyzingJob.setActive(false);
          stopAnalyzingRepository.save(analyzingJob);
        }
      }
    } else {
      throw new ProjectNotFoundException("Can't stop analyze a non-existing project.");
    }
  }
}
