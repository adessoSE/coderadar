package io.reflectoring.coderadar.graph.analyzer.service;

import io.reflectoring.coderadar.analyzer.port.driven.StartAnalyzingPort;
import io.reflectoring.coderadar.analyzer.port.driver.StartAnalyzingCommand;
import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzingJobEntity;
import io.reflectoring.coderadar.graph.analyzer.repository.AnalyzingJobRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StartAnalyzingAdapter implements StartAnalyzingPort {
  private final ProjectRepository projectRepository;
  private final AnalyzingJobRepository analyzingJobRepository;

  @Autowired
  public StartAnalyzingAdapter(
      ProjectRepository projectRepository, AnalyzingJobRepository analyzingJobRepository) {
    this.projectRepository = projectRepository;
    this.analyzingJobRepository = analyzingJobRepository;
  }

  @Override
  public Long start(StartAnalyzingCommand command, Long projectId) throws ProjectNotFoundException {
    AnalyzingJobEntity analyzingJob = new AnalyzingJobEntity();
    analyzingJob.setRescan(command.getRescan());
    analyzingJob.setFrom(command.getFrom());
    analyzingJob.setActive(true);

    Optional<ProjectEntity> persistedProject = projectRepository.findProjectById(projectId);

    if (persistedProject.isPresent()) {
      if (persistedProject.get().getAnalyzingJob() == null) {
        analyzingJob.setProject(persistedProject.get());
        return analyzingJobRepository.save(analyzingJob).getId();
      } else {
        persistedProject.get().getAnalyzingJob().setActive(true);
        analyzingJobRepository.save(persistedProject.get().getAnalyzingJob());
        return persistedProject.get().getAnalyzingJob().getId();
      }
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }
}
