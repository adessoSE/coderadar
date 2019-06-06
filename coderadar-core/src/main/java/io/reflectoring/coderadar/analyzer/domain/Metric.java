package io.reflectoring.coderadar.analyzer.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Metric {
  private Long id;

  private String name;

  @Relationship(type = "HAS_LOCALIZATION")
  private List<Finding> findings;
}
