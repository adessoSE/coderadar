package io.reflectoring.coderadar.domain;

import io.reflectoring.coderadar.plugin.api.Finding;
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
