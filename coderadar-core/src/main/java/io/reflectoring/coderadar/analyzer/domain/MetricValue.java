package io.reflectoring.coderadar.analyzer.domain;

import io.reflectoring.coderadar.projectadministration.domain.Commit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetricValue {
  private Long id;
  private String name;
  private Long value;

  private Commit commit;

  private List<Finding> findings = new LinkedList<>();

  private Long fileId;
}
