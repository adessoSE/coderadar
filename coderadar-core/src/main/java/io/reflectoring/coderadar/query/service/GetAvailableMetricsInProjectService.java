package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.query.port.driven.GetAvailableMetricsInProjectPort;
import io.reflectoring.coderadar.query.port.driver.GetAvailableMetricsInProjectUseCase;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetAvailableMetricsInProjectService implements GetAvailableMetricsInProjectUseCase {
  private final GetAvailableMetricsInProjectPort getAvailableMetricsInProjectPort;
  private final GetProjectPort getProjectPort;

  public GetAvailableMetricsInProjectService(
      GetAvailableMetricsInProjectPort getAvailableMetricsInProjectPort,
      GetProjectPort getProjectPort) {
    this.getAvailableMetricsInProjectPort = getAvailableMetricsInProjectPort;
    this.getProjectPort = getProjectPort;
  }

  @Override
  public List<String> get(long projectId) {
    if (getProjectPort.existsById(projectId)) {
      return getAvailableMetricsInProjectPort.get(projectId);
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }
}
