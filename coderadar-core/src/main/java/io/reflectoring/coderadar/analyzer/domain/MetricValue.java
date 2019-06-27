package io.reflectoring.coderadar.analyzer.domain;

import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetricValue {
  private Long id;
  private String name;
  private Long value;

  private Commit commit;

  private List<Finding> findings = new LinkedList<>();
}
