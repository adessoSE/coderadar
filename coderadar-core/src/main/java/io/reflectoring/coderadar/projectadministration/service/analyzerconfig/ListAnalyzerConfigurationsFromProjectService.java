package io.reflectoring.coderadar.projectadministration.service.analyzerconfig;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationsFromProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationResponse;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationsFromProjectUseCase;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("ListAnalyzerConfigurationsFromProjectService")
public class ListAnalyzerConfigurationsFromProjectService
    implements GetAnalyzerConfigurationsFromProjectUseCase {
  private final GetAnalyzerConfigurationsFromProjectPort port;
  private final GetProjectPort getProjectPort;

  @Autowired
  public ListAnalyzerConfigurationsFromProjectService(
      @Qualifier("GetAnalyzerConfigurationsFromProjectServiceNeo4j")
          GetAnalyzerConfigurationsFromProjectPort port,
      @Qualifier("GetProjectServiceNeo4j") GetProjectPort getProjectPort) {
    this.port = port;
    this.getProjectPort = getProjectPort;
  }

  @Override
  public List<GetAnalyzerConfigurationResponse> get(Long projectId)
      throws ProjectNotFoundException {
    if (getProjectPort.get(projectId).isPresent()) {
      List<GetAnalyzerConfigurationResponse> configurations = new ArrayList<>();
      for (AnalyzerConfiguration analyzerConfiguration : port.get(projectId)) {
        configurations.add(
            new GetAnalyzerConfigurationResponse(
                analyzerConfiguration.getId(),
                analyzerConfiguration.getAnalyzerName(),
                analyzerConfiguration.getEnabled()));
      }
      return configurations;
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }
}
