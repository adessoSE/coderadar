package io.reflectoring.coderadar.query.port.driven;

import io.reflectoring.coderadar.query.domain.MetricValueForCommit;
import io.reflectoring.coderadar.query.domain.MetricsTreeNodeType;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MetricTree {
  private String name;
  private MetricsTreeNodeType type;
  private List<MetricValueForCommit> metrics = new ArrayList<>();
  private List<MetricTree> children = new ArrayList<>();
}
