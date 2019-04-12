package org.wickedsource.coderadar.query.port.driver;

import lombok.Value;
import org.wickedsource.coderadar.metricquery.rest.commit.Interval;

import java.util.Date;

@Value
public class GetHistoryOfMetricCommand {
    Long projectId;
    String metricName;
    Date start;
    Date end;
    Interval interval;
}
