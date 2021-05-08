package io.reflectoring.coderadar.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class MetricValueForCommit {
  private String metricName;
  private long value;
}
