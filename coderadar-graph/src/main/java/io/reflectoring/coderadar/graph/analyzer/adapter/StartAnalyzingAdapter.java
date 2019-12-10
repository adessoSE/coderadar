package io.reflectoring.coderadar.graph.analyzer.adapter;

import io.reflectoring.coderadar.analyzer.port.driven.StartAnalyzingPort;
import io.reflectoring.coderadar.analyzer.port.driver.StartAnalyzingCommand;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StartAnalyzingAdapter implements StartAnalyzingPort {
  private final ProjectRepository projectRepository;

  public StartAnalyzingAdapter(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public void start(StartAnalyzingCommand command, Long projectId) {
    if (!projectRepository.existsById(projectId)) {
      throw new ProjectNotFoundException(projectId);
    }
    projectRepository.setAnalyzingStatus(projectId, true);
  }
}
