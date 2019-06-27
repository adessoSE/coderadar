package io.reflectoring.coderadar.analyzer.domain;

import lombok.Data;

@Data
public class Finding {
  private Long id;
  private Integer lineStart;
  private Integer lineEnd;
  private Integer charStart;
  private Integer charEnd;

  private MetricValue metricValue;
}
