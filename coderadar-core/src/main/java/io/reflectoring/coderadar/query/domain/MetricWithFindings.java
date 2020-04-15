package io.reflectoring.coderadar.query.domain;

import io.reflectoring.coderadar.analyzer.domain.Finding;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetricWithFindings {
  private String name;
  private long value;
  private List<Finding> findings;
}
