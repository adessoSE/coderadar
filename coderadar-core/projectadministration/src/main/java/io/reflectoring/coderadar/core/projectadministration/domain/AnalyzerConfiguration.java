package io.reflectoring.coderadar.core.projectadministration.domain;

import javax.persistence.*;
import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;

/** An AnalyzerConfiguration stores the configuration for a single analyzer plugin in a project. */
@NodeEntity
@Data
public class AnalyzerConfiguration {
  private Long id;
  private String analyzerName;
  private Boolean enabled;
}
