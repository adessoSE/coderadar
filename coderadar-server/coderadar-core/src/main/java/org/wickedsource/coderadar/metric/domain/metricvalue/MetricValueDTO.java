package org.wickedsource.coderadar.metric.domain.metricvalue;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class MetricValueDTO {

  private String metric;

  private Long value;

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
