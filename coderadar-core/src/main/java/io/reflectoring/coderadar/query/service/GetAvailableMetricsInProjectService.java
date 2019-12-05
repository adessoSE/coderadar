package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.query.port.driven.GetAvailableMetricsInProjectPort;
import io.reflectoring.coderadar.query.port.driver.GetAvailableMetricsInProjectUseCase;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetAvailableMetricsInProjectService implements GetAvailableMetricsInProjectUseCase {
  private final GetAvailableMetricsInProjectPort getAvailableMetricsInProjectPort;

  public GetAvailableMetricsInProjectService(
      GetAvailableMetricsInProjectPort getAvailableMetricsInProjectPort) {
    this.getAvailableMetricsInProjectPort = getAvailableMetricsInProjectPort;
  }

  @Override
  public List<String> get(Long projectId) {
    return getAvailableMetricsInProjectPort.get(projectId);
  }
}
