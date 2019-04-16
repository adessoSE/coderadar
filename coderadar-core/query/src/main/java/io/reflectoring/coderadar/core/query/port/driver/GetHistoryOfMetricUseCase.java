package io.reflectoring.coderadar.core.query.port.driver;


import io.reflectoring.coderadar.core.query.domain.Series;

public interface GetHistoryOfMetricUseCase {
    Series get(GetHistoryOfMetricCommand command);
}
