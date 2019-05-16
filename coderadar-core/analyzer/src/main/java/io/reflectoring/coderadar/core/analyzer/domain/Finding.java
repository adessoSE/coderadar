package io.reflectoring.coderadar.core.analyzer.domain;

import lombok.Data;

@Data
public class Finding {

  private MetricValue id;

  private Integer lineStart;

  private Integer lineEnd;

  private Integer charStart;

  private Integer charEnd;
}
