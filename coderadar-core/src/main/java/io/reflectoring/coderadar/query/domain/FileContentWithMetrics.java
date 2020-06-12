package io.reflectoring.coderadar.query.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileContentWithMetrics {
  private String content;
  private List<MetricWithFindings> metrics;
}
