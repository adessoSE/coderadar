package org.wickedsource.coderadar.analyzer.plugin.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FileMetrics {

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

    /**
     * Adds the given metrics to the metrics stored in this object.
     *
     * @param metrics the metrics to add to this FileMetrics object
     */
    public void add(FileMetrics metrics) {
        for (Metric metric : metrics.getMetrics()) {
            Long currentValue = metricValues.get(metric);
            if (currentValue == null) {
                currentValue = 0l;
            }
            metricValues.put(metric, currentValue + metrics.getMetricValue(metric));
        }
    }

}
