package org.wickedsource.coderadar.metricquery.rest.commit.metric;

import javax.validation.constraints.NotNull;
import org.wickedsource.coderadar.metricquery.rest.commit.DateRange;
import org.wickedsource.coderadar.metricquery.rest.commit.Interval;

/** Provides parameters to query for a history of values for a specific metric. */
public class CommitMetricsHistoryQuery {

  @NotNull private String metric;

  @NotNull private DateRange dateRange;

  @NotNull private Interval interval;

  public CommitMetricsHistoryQuery() {}

  public String getMetric() {
    return metric;
  }

  public void setMetric(String metric) {
    this.metric = metric;
  }

  public DateRange getDateRange() {
    return dateRange;
  }

  public void setDateRange(DateRange dateRange) {
    this.dateRange = dateRange;
  }

  public Interval getInterval() {
    return interval;
  }

  public void setInterval(Interval interval) {
    this.interval = interval;
  }
}
