package io.reflectoring.coderadar.projectadministration.service.analyzerconfig;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.CreateAnalyzerConfigurationPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.UpdateProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationUseCase;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateAnalyzerConfigurationService implements CreateAnalyzerConfigurationUseCase {

  private final CreateAnalyzerConfigurationPort createAnalyzerConfigurationPort;
  private final UpdateProjectPort updateProjectPort;
  private final GetProjectPort getProjectPort;

  @Autowired
  public CreateAnalyzerConfigurationService(
      CreateAnalyzerConfigurationPort createAnalyzerConfigurationPort,
      UpdateProjectPort updateProjectPort,
      GetProjectPort getProjectPort) {

    this.createAnalyzerConfigurationPort = createAnalyzerConfigurationPort;
    this.updateProjectPort = updateProjectPort;
    this.getProjectPort = getProjectPort;
  }

  @Override
  public Long create(CreateAnalyzerConfigurationCommand command, Long projectId)
      throws ProjectNotFoundException {
    AnalyzerConfiguration analyzerConfiguration = new AnalyzerConfiguration();
    analyzerConfiguration.setEnabled(command.getEnabled());
    analyzerConfiguration.setAnalyzerName(command.getAnalyzerName());

    Optional<Project> project = getProjectPort.get(projectId);

    if (project.isPresent()) {
      analyzerConfiguration.setProject(project.get());

      project.get().getAnalyzerConfigurations().add(analyzerConfiguration);
      updateProjectPort.update(project.get());

      return createAnalyzerConfigurationPort.create(analyzerConfiguration);
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }
}
