package io.reflectoring.coderadar.graph.query.domain;

import io.reflectoring.coderadar.query.domain.MetricsTreeNodeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetricTreeQueryResult {
  private String name;
  private MetricsTreeNodeType type;
  private List<MetricValueForCommitQueryResult> metrics;
  private List<MetricTreeQueryResult> children;
}
