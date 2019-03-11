package org.wickedsource.coderadar.metricquery.rest.tree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Payload for a node within a metrics tree. */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommitMetricsPayload implements MetricsTreePayload<CommitMetricsPayload> {

  private MetricValuesSet metrics = new MetricValuesSet();

  @Override
  public void add(CommitMetricsPayload payload) {
    metrics.add(payload.getMetrics());
  }
}
