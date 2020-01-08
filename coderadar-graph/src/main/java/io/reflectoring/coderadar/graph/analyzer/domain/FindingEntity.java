package io.reflectoring.coderadar.graph.analyzer.domain;

import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;

/** @see io.reflectoring.coderadar.analyzer.domain.Finding */
@NodeEntity
@Data
public class FindingEntity {
  private Long id;
  private Integer lineStart;
  private Integer lineEnd;
  private Integer charStart;
  private Integer charEnd;
}
