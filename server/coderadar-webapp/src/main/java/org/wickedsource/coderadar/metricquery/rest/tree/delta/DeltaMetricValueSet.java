package org.wickedsource.coderadar.metricquery.rest.tree.delta;

import org.wickedsource.coderadar.metricquery.rest.tree.MetricValuesSet;

/**
 * Contains metrics for two commits.
 */
public class DeltaMetricValueSet {

    private MetricValuesSet metricValues1;

    private MetricValuesSet metricValues2;

    public MetricValuesSet getMetricValues1() {
        return metricValues1;
    }

    public void setMetricValues1(MetricValuesSet metricValues1) {
        this.metricValues1 = metricValues1;
    }

    public MetricValuesSet getMetricValues2() {
        return metricValues2;
    }

    public void setMetricValues2(MetricValuesSet metricValues2) {
        this.metricValues2 = metricValues2;
    }
}
