package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.query.domain.Series;
import io.reflectoring.coderadar.query.port.driven.GetHistoryOfMetricPort;
import io.reflectoring.coderadar.query.port.driver.GetHistoryOfMetricCommand;
import io.reflectoring.coderadar.query.port.driver.GetHistoryOfMetricUseCase;
import org.springframework.stereotype.Service;

// TODO: Implement
@Service
public class GetHistoryOfMetricService implements GetHistoryOfMetricUseCase {
  private final GetHistoryOfMetricPort getHistoryOfMetricPort;

  public GetHistoryOfMetricService(GetHistoryOfMetricPort getHistoryOfMetricPort) {
    this.getHistoryOfMetricPort = getHistoryOfMetricPort;
  }

  @Override
  public Series get(GetHistoryOfMetricCommand command, Long projectId) {
    return getHistoryOfMetricPort.get(command);
  }
}
