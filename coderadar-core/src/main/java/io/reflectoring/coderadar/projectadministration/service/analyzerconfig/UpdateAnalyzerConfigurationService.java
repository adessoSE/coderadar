package io.reflectoring.coderadar.projectadministration.service.analyzerconfig;

import io.reflectoring.coderadar.analyzer.service.ListAnalyzersService;
import io.reflectoring.coderadar.plugin.api.AnalyzerConfigurationException;
import io.reflectoring.coderadar.projectadministration.AnalyzerConfigurationNotFoundException;
import io.reflectoring.coderadar.projectadministration.AnalyzerNotFoundException;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationPort;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.UpdateAnalyzerConfigurationPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.update.UpdateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.update.UpdateAnalyzerConfigurationUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateAnalyzerConfigurationService implements UpdateAnalyzerConfigurationUseCase {

  private final GetAnalyzerConfigurationPort getAnalyzerConfigurationPort;
  private final UpdateAnalyzerConfigurationPort updateAnalyzerConfigurationPort;
  private final ListAnalyzersService listAnalyzerService;
  private final ListAnalyzerConfigurationsService listAnalyzerConfigurationsFromProjectService;
  private final GetProjectPort getProjectPort;

  private static final Logger logger =
      LoggerFactory.getLogger(UpdateAnalyzerConfigurationService.class);

  @Override
  public void update(
      UpdateAnalyzerConfigurationCommand command, long configurationId, long projectId) {
    if (!getProjectPort.existsById(projectId)) {
      throw new ProjectNotFoundException(projectId);
    }
    if (!getAnalyzerConfigurationPort.existsById(configurationId)) {
      throw new AnalyzerConfigurationNotFoundException(configurationId);
    }

    List<String> analyzers = listAnalyzerService.listAvailableAnalyzers();
    if (analyzers.contains(command.getAnalyzerName())) {
      if (listAnalyzerConfigurationsFromProjectService.get(projectId).stream()
          .noneMatch(
              a ->
                  a.getAnalyzerName().equals(command.getAnalyzerName())
                      && a.getId() != configurationId)) {
        updateAnalyzerConfigurationPort.update(configurationId, command);
        logger.info(
            "Updated analyzerConfiguration with id {} for project with id {}",
            configurationId,
            projectId);
      } else {
        throw new AnalyzerConfigurationException(
            "An analyzer with this name is already configured for the project!");
      }
    } else {
      throw new AnalyzerNotFoundException(command.getAnalyzerName());
    }
  }
}
