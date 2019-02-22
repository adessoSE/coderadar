package org.wickedsource.coderadar.metric.rest;

public class MetricResource {

  private String metricName;

  public MetricResource() {}

  public MetricResource(String metricName) {
    this.metricName = metricName;
  }

  public String getMetricName() {
    return metricName;
  }

  public void setMetricName(String metricName) {
    this.metricName = metricName;
  }
}
