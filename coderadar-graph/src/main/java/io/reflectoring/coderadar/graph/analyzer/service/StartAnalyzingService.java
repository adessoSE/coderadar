package io.reflectoring.coderadar.graph.analyzer.service;

import io.reflectoring.coderadar.analyzer.domain.AnalyzingJob;
import io.reflectoring.coderadar.analyzer.port.driven.StartAnalyzingPort;
import io.reflectoring.coderadar.analyzer.port.driver.StartAnalyzingCommand;
import io.reflectoring.coderadar.graph.analyzer.repository.StartAnalyzingRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("StartAnalyzingServiceNeo4j")
public class StartAnalyzingService implements StartAnalyzingPort {
  private final GetProjectRepository getProjectRepository;
  private final StartAnalyzingRepository startAnalyzingRepository;

  @Autowired
  public StartAnalyzingService(
      GetProjectRepository getProjectRepository,
      StartAnalyzingRepository startAnalyzingRepository) {
    this.getProjectRepository = getProjectRepository;
    this.startAnalyzingRepository = startAnalyzingRepository;
  }

  @Override
  public Long start(StartAnalyzingCommand command, Long projectId) {
    AnalyzingJob analyzingJob = new AnalyzingJob();
    analyzingJob.setRescan(command.getRescan());
    analyzingJob.setFrom(command.getFrom());
    analyzingJob.setActive(true);

    Optional<Project> persistedProject = getProjectRepository.findById(projectId);

    if (persistedProject.isPresent()) {
      analyzingJob.setProject(persistedProject.get());
      return startAnalyzingRepository.save(analyzingJob).getId();
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }
}
