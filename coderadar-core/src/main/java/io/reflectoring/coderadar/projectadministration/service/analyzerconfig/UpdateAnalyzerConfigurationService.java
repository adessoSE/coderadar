package io.reflectoring.coderadar.projectadministration.service.analyzerconfig;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.analyzer.service.ListAnalyzersService;
import io.reflectoring.coderadar.plugin.api.AnalyzerConfigurationException;
import io.reflectoring.coderadar.projectadministration.AnalyzerNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationPort;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.UpdateAnalyzerConfigurationPort;
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

  private static final Logger logger =
      LoggerFactory.getLogger(UpdateAnalyzerConfigurationService.class);

  @Override
  public void update(
      UpdateAnalyzerConfigurationCommand command, long configurationId, long projectId) {
    AnalyzerConfiguration analyzerConfiguration =
        getAnalyzerConfigurationPort.getAnalyzerConfiguration(configurationId);

    List<String> analyzers = listAnalyzerService.listAvailableAnalyzers();
    if (analyzers.contains(command.getAnalyzerName())) {
      if (listAnalyzerConfigurationsFromProjectService.get(projectId).stream()
          .noneMatch(
              a ->
                  a.getAnalyzerName().equals(command.getAnalyzerName())
                      && a.getId() != analyzerConfiguration.getId())) {
        analyzerConfiguration.setAnalyzerName(command.getAnalyzerName());
        analyzerConfiguration.setEnabled(command.isEnabled());
        updateAnalyzerConfigurationPort.update(analyzerConfiguration);
        logger.info(
            "Updated analyzerConfiguration with id {} for project with id {}",
            analyzerConfiguration.getId(),
            projectId);
      } else {
        throw new AnalyzerConfigurationException(
            "An analyzer with this name is already configured for the project!");
      }
    } else {
      throw new AnalyzerNotFoundException(
          "Analyzer with name " + command.getAnalyzerName() + " not found");
    }
  }
}
