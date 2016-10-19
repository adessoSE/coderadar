package org.wickedsource.coderadar.metricquery.rest.tree;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains values for a set of metrics.
 */
public class MetricValuesSet {

    private Map<String, Long> metricValues = new HashMap<>();

    public void setMetricValue(String metric, Long value) {
        this.metricValues.put(metric, value);
    }

    public Long getMetricValue(String metric) {
        return this.metricValues.get(metric);
    }

    public Map<String, Long> getMetricValues() {
        return metricValues;
    }

    public void setMetricValues(Map<String, Long> metricValues) {
        this.metricValues = metricValues;
    }
}
