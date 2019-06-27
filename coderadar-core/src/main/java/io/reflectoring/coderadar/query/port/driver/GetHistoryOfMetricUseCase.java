package io.reflectoring.coderadar.query.port.driver;

import io.reflectoring.coderadar.query.domain.Series;

public interface GetHistoryOfMetricUseCase {
  Series get(GetHistoryOfMetricCommand command);
}
