package io.reflectoring.coderadar.graph.projectadministration.domain;

import io.reflectoring.coderadar.graph.analyzer.domain.MetricValueEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/** @see io.reflectoring.coderadar.projectadministration.domain.Commit */
@NodeEntity
@Data
@EqualsAndHashCode
@ToString
public class CommitEntity {
  private Long id;
  private String name;
  private Date timestamp;
  private String comment;
  private String author;
  private boolean merged = false;
  private boolean analyzed = false;

  @Relationship(type = "IS_CHILD_OF")
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private List<CommitEntity> parents = new ArrayList<>();

  @Relationship(direction = Relationship.INCOMING, type = "CHANGED_IN")
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private List<FileToCommitRelationshipEntity> touchedFiles = new ArrayList<>();

  @Relationship(direction = Relationship.INCOMING, type = "VALID_FOR")
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private List<MetricValueEntity> metricValues = new ArrayList<>();
}
