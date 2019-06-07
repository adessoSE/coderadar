package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.query.domain.Series;
import io.reflectoring.coderadar.query.port.driven.GetHistoryOfMetricPort;
import io.reflectoring.coderadar.query.port.driver.GetHistoryOfMetricCommand;
import io.reflectoring.coderadar.query.port.driver.GetHistoryOfMetricUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetHistoryOfMetricService implements GetHistoryOfMetricUseCase {
  private final GetHistoryOfMetricPort getHistoryOfMetricPort;

  @Autowired
  public GetHistoryOfMetricService(GetHistoryOfMetricPort getHistoryOfMetricPort) {
    this.getHistoryOfMetricPort = getHistoryOfMetricPort;
  }

  @Override
  public Series get(GetHistoryOfMetricCommand command) {
    return getHistoryOfMetricPort.get(command);
  }
}
