package io.reflectoring.coderadar.core.query.service;

import io.reflectoring.coderadar.core.query.port.driven.GetAvailableMetricsInProjectPort;
import io.reflectoring.coderadar.core.query.port.driver.GetAvailableMetricsInProjectUseCase;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("GetAvailableMetricsInProjectService")
public class GetAvailableMetricsInProjectService implements GetAvailableMetricsInProjectUseCase {
  private final GetAvailableMetricsInProjectPort getAvailableMetricsInProjectPort;

  @Autowired
  public GetAvailableMetricsInProjectService(
      @Qualifier("GetAvailableMetricsInProjectServiceNeo4j") GetAvailableMetricsInProjectPort getAvailableMetricsInProjectPort) {
    this.getAvailableMetricsInProjectPort = getAvailableMetricsInProjectPort;
  }

  @Override
  public List<String> get(Long projectId) {
    return getAvailableMetricsInProjectPort.get(projectId);
  }
}
