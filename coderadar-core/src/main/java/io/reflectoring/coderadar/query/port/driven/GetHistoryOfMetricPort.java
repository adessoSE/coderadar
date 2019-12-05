package io.reflectoring.coderadar.query.port.driven;

import io.reflectoring.coderadar.query.domain.Series;
import io.reflectoring.coderadar.query.port.driver.GetHistoryOfMetricCommand;

// TODO: Implement
public interface GetHistoryOfMetricPort {
  Series get(GetHistoryOfMetricCommand command);
}
