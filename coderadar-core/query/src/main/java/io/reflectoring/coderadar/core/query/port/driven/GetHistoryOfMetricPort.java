package io.reflectoring.coderadar.core.query.port.driven;

import io.reflectoring.coderadar.core.query.domain.Series;
import io.reflectoring.coderadar.core.query.port.driver.GetHistoryOfMetricCommand;

public interface GetHistoryOfMetricPort {
  Series get(GetHistoryOfMetricCommand command);
}
