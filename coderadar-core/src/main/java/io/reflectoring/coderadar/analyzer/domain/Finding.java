package io.reflectoring.coderadar.analyzer.domain;

import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
@Data
public class Finding {
  private Long id;
  private Integer lineStart;
  private Integer lineEnd;
  private Integer charStart;
  private Integer charEnd;
}
