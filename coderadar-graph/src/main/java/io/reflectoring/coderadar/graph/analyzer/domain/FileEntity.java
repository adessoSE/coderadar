package io.reflectoring.coderadar.graph.analyzer.domain;

import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.LinkedList;
import java.util.List;

/** Represents a file in a VCS repository. */
@NodeEntity
@Data
public class FileEntity {
  private Long id;
  private String path;

  @Relationship(type = "MEASURED_BY")
  private List<MetricValueEntity> metricValues = new LinkedList<>();

  @Relationship private List<FileToCommitRelationshipEntity> commits = new LinkedList<>();
}
