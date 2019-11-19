package io.reflectoring.coderadar.graph.projectadministration.domain;

import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/** @see io.reflectoring.coderadar.projectadministration.domain.FilePattern */
@NodeEntity
@Data
public class FilePatternEntity {
  private Long id;
  private String pattern;
  private InclusionType inclusionType;

  @Relationship(direction = Relationship.INCOMING, type = "HAS")
  private ProjectEntity project;
}
