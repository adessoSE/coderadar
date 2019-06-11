package io.reflectoring.coderadar.analyzer.domain;

import java.util.LinkedList;
import java.util.List;
import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/** Represents a file in a VCS repository. */
@NodeEntity
@Data
public class File {
  private Long id;
  private String path;

  @Relationship(type = "MEASURED_BY")
  private List<MetricValue> metricValues = new LinkedList<>();

  @Relationship
  private List<FileToCommitRelationship> commits = new LinkedList<>();
}
