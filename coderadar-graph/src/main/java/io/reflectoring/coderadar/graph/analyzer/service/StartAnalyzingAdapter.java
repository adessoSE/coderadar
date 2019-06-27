package io.reflectoring.coderadar.graph.analyzer.service;

import io.reflectoring.coderadar.analyzer.port.driven.StartAnalyzingPort;
import io.reflectoring.coderadar.analyzer.port.driver.StartAnalyzingCommand;
import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzingJobEntity;
import io.reflectoring.coderadar.graph.analyzer.repository.StartAnalyzingRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StartAnalyzingAdapter implements StartAnalyzingPort {
  private final GetProjectRepository getProjectRepository;
  private final StartAnalyzingRepository startAnalyzingRepository;

  @Autowired
  public StartAnalyzingAdapter(
      GetProjectRepository getProjectRepository,
      StartAnalyzingRepository startAnalyzingRepository) {
    this.getProjectRepository = getProjectRepository;
    this.startAnalyzingRepository = startAnalyzingRepository;
  }

  @Override
  public Long start(StartAnalyzingCommand command, Long projectId) throws ProjectNotFoundException {
    AnalyzingJobEntity analyzingJob = new AnalyzingJobEntity();
    analyzingJob.setRescan(command.getRescan());
    analyzingJob.setFrom(command.getFrom());
    analyzingJob.setActive(true);

    Optional<ProjectEntity> persistedProject = getProjectRepository.findById(projectId);

    if (persistedProject.isPresent()) {
      analyzingJob.setProject(persistedProject.get());
      return startAnalyzingRepository.save(analyzingJob).getId();
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }
}
