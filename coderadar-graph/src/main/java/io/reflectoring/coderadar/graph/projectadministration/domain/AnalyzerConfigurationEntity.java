package io.reflectoring.coderadar.graph.projectadministration.domain;

import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/** An AnalyzerConfiguration stores the configuration for a single analyzer plugin in a project. */
@NodeEntity
@Data
public class AnalyzerConfigurationEntity {
  private Long id;
  private String analyzerName;
  private Boolean enabled;

  @Relationship(direction = Relationship.INCOMING, type = "HAS")
  private ProjectEntity project;

  @Relationship("CONTENT_FROM")
  private AnalyzerConfigurationFileEntity analyzerConfigurationFile;
}
