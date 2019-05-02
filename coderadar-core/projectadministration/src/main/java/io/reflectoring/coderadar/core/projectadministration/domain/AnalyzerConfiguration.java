package io.reflectoring.coderadar.core.projectadministration.domain;

import javax.persistence.*;
import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

/** An AnalyzerConfiguration stores the configuration for a single analyzer plugin in a project. */
@NodeEntity
@Data
public class AnalyzerConfiguration {
  private Long id;
  private String analyzerName;
  private Boolean enabled;

  @Relationship(direction = INCOMING)
  private Project project;

  @Relationship("CONTENT_FROM")
  private AnalyzerConfigurationFile analyzerConfigurationFile;
}
