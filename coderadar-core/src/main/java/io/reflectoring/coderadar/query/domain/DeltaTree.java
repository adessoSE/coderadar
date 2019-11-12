package io.reflectoring.coderadar.query.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeltaTree {
  private String name;
  private MetricsTreeNodeType type;
  private List<MetricValueForCommit> commit1Metrics = new ArrayList<>();
  private List<MetricValueForCommit> commit2Metrics = new ArrayList<>();
  private String renamedFrom;
  private String renamedTo;
  private Changes changes;
  private List<DeltaTree> children = new ArrayList<>();
}
