package io.reflectoring.coderadar.graph.analyzer.domain;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/** @see io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration */
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
