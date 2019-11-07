package io.reflectoring.coderadar.graph.projectadministration.domain;

import io.reflectoring.coderadar.graph.analyzer.domain.MetricValueEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/** Represents a file in a VCS repository. */
@NodeEntity
@Data
@EqualsAndHashCode
public class FileEntity {
  private Long id;
  private String path;

  @Relationship(type = "MEASURED_BY")
  @ToString.Exclude
  private List<MetricValueEntity> metricValues = new LinkedList<>();

  @EqualsAndHashCode.Exclude
  @Relationship(type = "CHANGED_IN")
  @ToString.Exclude
  private List<FileToCommitRelationshipEntity> commits = new LinkedList<>();

  @EqualsAndHashCode.Exclude
  @Relationship(type = "RENAMED_FROM")
  @ToString.Exclude
  private List<FileEntity> oldFiles = new ArrayList<>();
}
