package io.reflectoring.coderadar.query.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

/** Contains values for a set of metrics. */
public class MetricValuesSet {

  private Map<String, Long> metricValues = new HashMap<>();

  @JsonAnySetter
  public void setMetricValue(String metric, Long value) {
    this.metricValues.put(metric, value);
  }

  public Long getMetricValue(String metric) {
    return this.metricValues.get(metric);
  }

  @JsonAnyGetter
  public Map<String, Long> getMetricValues() {
    return metricValues;
  }

  public void setMetricValues(Map<String, Long> metricValues) {
    this.metricValues = metricValues;
  }

  public void add(MetricValuesSet metrics) {
    for (Map.Entry<String, Long> entry : metrics.getMetricValues().entrySet()) {
      Long currentValue = this.getMetricValue(entry.getKey());
      if (currentValue == null) {
        currentValue = 0L;
      }
      this.setMetricValue(entry.getKey(), currentValue + entry.getValue());
    }
  }
}
