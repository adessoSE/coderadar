package io.reflectoring.coderadar.query.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
