package org.wickedsource.coderadar.metric.domain.metricvalue;

public class MetricValueDTO {

  private String metric;

  private Long value;

  public MetricValueDTO() {}

  public MetricValueDTO(String metricName, Long value) {
    this.metric = metricName;
    this.value = value;
  }

  public String getMetric() {
    return metric;
  }

  public Long getValue() {
    return value;
  }

  public void setMetric(String metric) {
    this.metric = metric;
  }

  public void setValue(Long value) {
    this.value = value;
  }
}
