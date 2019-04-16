package io.reflectoring.coderadar.core.query.port.driver;

import io.reflectoring.coderadar.core.query.domain.Interval;
import lombok.Value;

import java.util.Date;

@Value
public class GetHistoryOfMetricCommand {
    Long projectId;
    String metricName;
    Date start;
    Date end;
    Interval interval;
}
