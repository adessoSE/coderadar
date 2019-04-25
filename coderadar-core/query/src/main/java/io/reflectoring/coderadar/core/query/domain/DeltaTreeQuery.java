package io.reflectoring.coderadar.core.query.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Provides parameters to query for values of one or more metrics values at the time of two specific
 * commits.
 */
@NoArgsConstructor
@Data
public class DeltaTreeQuery {

  @NotNull private String commit1;

  @NotNull private String commit2;

  @Size(min = 1)
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
