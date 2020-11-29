package io.reflectoring.coderadar.query.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeltaTree {
  private String name;
  private MetricTreeNodeType type;
  private List<MetricValueForCommit> commit1Metrics;
  private List<MetricValueForCommit> commit2Metrics;
  private String renamedFrom;
  private String renamedTo;
  private Changes changes;
  private List<DeltaTree> children = new ArrayList<>();
}
