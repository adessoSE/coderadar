package io.reflectoring.coderadar.query.port.driver;

import io.reflectoring.coderadar.query.domain.Changes;
import io.reflectoring.coderadar.query.domain.MetricValueForCommit;
import io.reflectoring.coderadar.query.domain.MetricsTreeNodeType;
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
