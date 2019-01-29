package org.wickedsource.coderadar.metricquery.rest.tree.delta;

import lombok.Data;
import org.wickedsource.coderadar.metricquery.rest.tree.MetricValuesSet;

/** Contains metrics for two commits. */
@Data
public class DeltaMetricValueSet {

  private MetricValuesSet metricValues1;

  private MetricValuesSet metricValues2;
}
