package io.reflectoring.coderadar.graph.projectadministration.domain;

import io.reflectoring.coderadar.graph.analyzer.domain.MetricValueEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

/** @see io.reflectoring.coderadar.projectadministration.domain.File */
@NodeEntity
@Data
@EqualsAndHashCode
public class FileEntity {
  private Long id;
  private String path;

  @Relationship(type = "MEASURED_BY")
  @ToString.Exclude
  private List<MetricValueEntity> metricValues = new ArrayList<>();

  @EqualsAndHashCode.Exclude
  @Relationship(type = "RENAMED_FROM")
  @ToString.Exclude
  private List<FileEntity> oldFiles = new ArrayList<>();
}
