package io.reflectoring.coderadar.graph.analyzer.domain;

import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/** @see io.reflectoring.coderadar.analyzer.domain.Finding */
@NodeEntity
@Data
public class FindingEntity {
  private Long id;
  private Integer lineStart;
  private Integer lineEnd;
  private Integer charStart;
  private Integer charEnd;

  @Relationship(direction = Relationship.INCOMING, type = "LOCATED_IN")
  private MetricValueEntity metricValue;
}
