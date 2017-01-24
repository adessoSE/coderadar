package org.wickedsource.coderadar.metricquery.rest.tree;

/** Payload for a node within a metrics tree. */
public class CommitMetricsPayload implements MetricsTreePayload<CommitMetricsPayload> {

  private MetricValuesSet metrics = new MetricValuesSet();

  public CommitMetricsPayload() {}

  public CommitMetricsPayload(MetricValuesSet metrics) {
    this.metrics = metrics;
  }

  @Override
  public void add(CommitMetricsPayload payload) {
    metrics.add(payload.getMetrics());
  }

  public MetricValuesSet getMetrics() {
    return metrics;
  }
}
