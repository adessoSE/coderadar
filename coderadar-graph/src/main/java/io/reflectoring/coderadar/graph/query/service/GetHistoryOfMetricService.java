package io.reflectoring.coderadar.graph.query.service;

import io.reflectoring.coderadar.core.query.domain.Series;
import io.reflectoring.coderadar.core.query.port.driven.GetHistoryOfMetricPort;
import io.reflectoring.coderadar.core.query.port.driver.GetHistoryOfMetricCommand;
import org.springframework.stereotype.Service;

@Service("GetHistoryOfMetricServiceNeo4j")
public class GetHistoryOfMetricService implements GetHistoryOfMetricPort {
    @Override
    public Series get(GetHistoryOfMetricCommand command) {
        return new Series();
    }
}
