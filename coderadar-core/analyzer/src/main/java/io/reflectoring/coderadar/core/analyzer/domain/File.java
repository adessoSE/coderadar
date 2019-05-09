package io.reflectoring.coderadar.core.analyzer.domain;

import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

/** Represents a file in a VCS repository. */
@NodeEntity
@Data
public class File {
  private Long id;
  private String path;

  @Relationship private List<MetricValue> metricValues;
}
