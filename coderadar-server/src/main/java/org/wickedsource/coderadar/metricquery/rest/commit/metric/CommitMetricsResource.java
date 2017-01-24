package org.wickedsource.coderadar.metricquery.rest.commit.metric;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.ResourceSupport;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Result of a query for values of selected metrics at the time of a given commit. */
@SuppressWarnings("unchecked")
public class CommitMetricsResource extends ResourceSupport {

  private Map<String, Long> metrics;

  public CommitMetricsResource() {}

  @JsonIgnore
  public void addMetricValues(List<MetricValueDTO> metricValuesPerCommit) {
    for (MetricValueDTO metricValue : metricValuesPerCommit) {
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
