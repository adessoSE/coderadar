package io.reflectoring.coderadar.graph.query.domain;

import io.reflectoring.coderadar.query.domain.MetricsTreeNodeType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class MetricTreeQueryResult {
  private String name;
  private MetricsTreeNodeType type;
  private List<MetricValueForCommitQueryResult> metrics;
  private List<MetricTreeQueryResult> children;
}
