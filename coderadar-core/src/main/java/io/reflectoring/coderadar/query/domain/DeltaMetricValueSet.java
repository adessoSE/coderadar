package io.reflectoring.coderadar.query.domain;

import lombok.Data;

/** Contains metrics for two commits. */
@Data
public class DeltaMetricValueSet {

  private MetricValuesSet metricValues1;

  private MetricValuesSet metricValues2;
}
