package io.reflectoring.coderadar.core.projectadministration.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Metric {
  private Long id;

  private String name;

  @Relationship(type = "HAS_LOCALIZATION") private List<Finding> findings;
}
