package io.reflectoring.coderadar.graph.query.service;

import io.reflectoring.coderadar.query.domain.Series;
import io.reflectoring.coderadar.query.port.driven.GetHistoryOfMetricPort;
import io.reflectoring.coderadar.query.port.driver.GetHistoryOfMetricCommand;
import org.springframework.stereotype.Service;

@Service
public class GetHistoryOfMetricAdapter implements GetHistoryOfMetricPort {

  // TODO
  @Override
  public Series get(GetHistoryOfMetricCommand command) {
    return new Series();
  }
}
