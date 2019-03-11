package org.wickedsource.coderadar.metricquery.rest.commit.metric;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.wickedsource.coderadar.metricquery.rest.commit.DateRange;
import org.wickedsource.coderadar.metricquery.rest.commit.Interval;

/** Provides parameters to query for a history of values for a specific metric. */
@Data
@NoArgsConstructor
public class CommitMetricsHistoryQuery {

  @NotNull private String metric;

  @NotNull private DateRange dateRange;

  @NotNull private Interval interval;
}
