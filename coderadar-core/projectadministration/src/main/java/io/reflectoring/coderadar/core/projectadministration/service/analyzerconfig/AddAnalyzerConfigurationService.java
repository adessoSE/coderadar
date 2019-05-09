package io.reflectoring.coderadar.core.projectadministration.service.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig.AddAnalyzerConfigurationPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.UpdateProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.AddAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.AddAnalyzerConfigurationUseCase;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddAnalyzerConfigurationService implements AddAnalyzerConfigurationUseCase {

  private final AddAnalyzerConfigurationPort addAnalyzerConfigurationPort;
  private final UpdateProjectPort updateProjectPort;
  private final GetProjectPort getProjectPort;

  @Autowired
  public AddAnalyzerConfigurationService(
      AddAnalyzerConfigurationPort addAnalyzerConfigurationPort,
      UpdateProjectPort updateProjectPort,
      GetProjectPort getProjectPort) {
    this.addAnalyzerConfigurationPort = addAnalyzerConfigurationPort;
    this.updateProjectPort = updateProjectPort;
    this.getProjectPort = getProjectPort;
  }

  @Override
  public Long add(AddAnalyzerConfigurationCommand command) {
    AnalyzerConfiguration analyzerConfiguration = new AnalyzerConfiguration();
    analyzerConfiguration.setEnabled(command.getEnabled());
    analyzerConfiguration.setAnalyzerName(command.getAnalyzerName());

    Optional<Project> project = getProjectPort.get(command.getProjectId());

    if (project.isPresent()) {
      project.get().getAnalyzerConfigurations().add(analyzerConfiguration);
      updateProjectPort.update(project.get());
    } else {
      throw new ProjectNotFoundException();
    }

    return addAnalyzerConfigurationPort.add(command.getProjectId(), analyzerConfiguration);
  }
}
