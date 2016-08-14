package org.wickedsource.coderadar.metricquery.commit.metric;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.ResourceSupport;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValuePerCommitDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores the result of a query for metric values of selected metrics aggregated by a selected commit.
 */
@SuppressWarnings("unchecked")
public class CommitMetricsOutputResource extends ResourceSupport {

    private Map<String, Long> metrics;

    public CommitMetricsOutputResource() {
    }

    @JsonIgnore
    public void addMetricValues(List<MetricValuePerCommitDTO> metricValuesPerCommit) {
        for (MetricValuePerCommitDTO metricValue : metricValuesPerCommit) {
            addMetricValue(metricValue.getMetric(), metricValue.getValue());
        }
    }

    public void addMetricValue(String metric, Long value) {
        initMetrics();
        metrics.put(metric, value);
    }

    private void initMetrics() {
        if (metrics == null) {
            metrics = new HashMap<>();
        }
    }

    public Map<String, Long> getMetrics() {
        return metrics;
    }

    public void setMetrics(Map<String, Long> metrics) {
        this.metrics = metrics;
    }

    public void addAbsentMetrics(List<String> metrics) {
        for (String metric : metrics) {
            if (this.metrics.get(metric) == null) {
                addMetricValue(metric, 0L);
            }
        }
    }
}
