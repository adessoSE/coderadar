package org.wickedsource.coderadar.metricquery.rest.tree.delta;

import org.wickedsource.coderadar.metricquery.rest.tree.MetricValuesSet;
import org.wickedsource.coderadar.metricquery.rest.tree.MetricsTreePayload;

/**
 * Payload class that contains a set of metric values for two commits, thus providing all numbers
 * needed for a delta analysis between two commits.
 */
public class DeltaTreePayload implements MetricsTreePayload<DeltaTreePayload> {

  private MetricValuesSet commit1Metrics;

  private MetricValuesSet commit2Metrics;

  private String renamedFrom;

  private String renamedTo;

  private Changes changes;

  @Override
  public void add(DeltaTreePayload payload) {}

  public MetricValuesSet getCommit1Metrics() {
    return commit1Metrics;
  }

  public void setCommit1Metrics(MetricValuesSet commit1Metrics) {
    this.commit1Metrics = commit1Metrics;
  }

  public MetricValuesSet getCommit2Metrics() {
    return commit2Metrics;
  }

  public void setCommit2Metrics(MetricValuesSet commit2Metrics) {
    this.commit2Metrics = commit2Metrics;
  }

  public String getRenamedFrom() {
    return renamedFrom;
  }

  public void setRenamedFrom(String renamedFrom) {
    this.renamedFrom = renamedFrom;
  }

  public String getRenamedTo() {
    return renamedTo;
  }

  public void setRenamedTo(String renamedTo) {
    this.renamedTo = renamedTo;
  }

  public Changes getChanges() {
    return changes;
  }

  public void setChanges(Changes changes) {
    this.changes = changes;
  }
}
