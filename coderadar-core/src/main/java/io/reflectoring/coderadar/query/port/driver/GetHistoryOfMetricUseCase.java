package io.reflectoring.coderadar.query.port.driver;

import io.reflectoring.coderadar.query.domain.Series;

// TODO: Implement
public interface GetHistoryOfMetricUseCase {
  Series get(GetHistoryOfMetricCommand command, Long projectId);
}
