package org.wickedsource.coderadar.analyzer.plugin.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MetricResults {

    private Map<Metric, Long> metricValues = new HashMap<>();

    public Set<Metric> getMetrics() {
        return metricValues.keySet();
    }

    public Long getMetricValue(Metric metric) {
        return metricValues.get(metric);
    }

    public void setMetricValue(Metric metric, Long value) {
        metricValues.put(metric, value);
    }

}
