package io.reflectoring.coderadar.analyzer.domain;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import java.util.Date;
import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@Data
@NodeEntity
public class AnalyzingJob {
  private Long id;
  private Date from; // TODO: Maybe use date converter.
  private boolean active;
  private boolean rescan;

  @Relationship(direction = Relationship.INCOMING, type = "HAS")
  private Project project;
}
