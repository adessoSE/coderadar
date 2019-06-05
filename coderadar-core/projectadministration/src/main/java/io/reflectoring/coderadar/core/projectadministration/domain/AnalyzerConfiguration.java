package io.reflectoring.coderadar.core.projectadministration.domain;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/** An AnalyzerConfiguration stores the configuration for a single analyzer plugin in a project. */
@NodeEntity
@Data
public class AnalyzerConfiguration {
  private Long id;
  private String analyzerName;
  private Boolean enabled;

  @Relationship(direction = INCOMING, type = "HAS")
  private Project project;

  @Relationship("CONTENT_FROM")
  private AnalyzerConfigurationFile analyzerConfigurationFile;
}
