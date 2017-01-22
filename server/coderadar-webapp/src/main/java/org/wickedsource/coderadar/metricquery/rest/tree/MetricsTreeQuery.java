package org.wickedsource.coderadar.metricquery.rest.tree;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Provides parameters to query for values of one or more metrics values at the time of a specific
 * commit.
 */
public class MetricsTreeQuery {

  @NotNull private String commit;

  @Size(min = 1)
  private List<String> metrics;

  public MetricsTreeQuery() {}

  private void initMetrics() {
    if (metrics == null) {
      metrics = new ArrayList<>();
    }
  }

  public void addMetrics(String... metrics) {
    initMetrics();
    this.metrics.addAll(Arrays.asList(metrics));
  }

  public void addMetric(String metric) {
    initMetrics();
    this.metrics.add(metric);
  }

  public String getCommit() {
    return commit;
  }

  public void setCommit(String commit) {
    this.commit = commit;
  }

  public List<String> getMetrics() {
    return metrics;
  }
}
