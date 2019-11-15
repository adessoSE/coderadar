package io.reflectoring.coderadar.analyzer.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Finding {
  private Long id;
  private Integer lineStart;
  private Integer lineEnd;
  private Integer charStart;
  private Integer charEnd;

  private MetricValue metricValue;

  public Finding(int lineStart, int lineEnd, int charStart, int charEnd) {
    this.lineStart = lineStart;
    this.lineEnd = lineEnd;
    this.charStart = charStart;
    this.charEnd = charEnd;
  }
}
