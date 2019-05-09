package io.reflectoring.coderadar.core.query.port.driver;

import io.reflectoring.coderadar.core.query.domain.Interval;
import java.util.Date;
import lombok.Value;

@Value
public class GetHistoryOfMetricCommand {
  Long projectId;
  String metricName;
  Date start;
  Date end;
  Interval interval;
}
