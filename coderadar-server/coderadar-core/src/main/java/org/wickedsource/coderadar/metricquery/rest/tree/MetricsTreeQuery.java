package org.wickedsource.coderadar.metricquery.rest.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Provides parameters to query for values of one or more metrics values at the time of a specific
 * commit.
 */
@NoArgsConstructor
public class MetricsTreeQuery {

  @Getter @Setter @NotNull private String commit;

  @Size(min = 1)
  @Getter
  private List<String> metrics;

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
}
