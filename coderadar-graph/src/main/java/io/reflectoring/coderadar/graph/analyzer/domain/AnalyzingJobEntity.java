package io.reflectoring.coderadar.graph.analyzer.domain;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Date;

@Data
@NodeEntity
public class AnalyzingJobEntity {
  private Long id;
  private Date from; // TODO: Maybe use date converter.
  private boolean active;
  private boolean rescan;

  @Relationship(direction = Relationship.INCOMING, type = "HAS")
  private ProjectEntity project;
}
