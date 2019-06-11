package io.reflectoring.coderadar.analyzer.domain;

import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
@Data
public class Finding {
  private Long id;
  private Integer lineStart;
  private Integer lineEnd;
  private Integer charStart;
  private Integer charEnd;

  @Relationship(direction = Relationship.INCOMING, type = "LOCATED_IN")
  private MetricValue metricValue;
}
