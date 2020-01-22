package io.reflectoring.coderadar.graph.projectadministration.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

/** @see io.reflectoring.coderadar.projectadministration.domain.Commit */
@NodeEntity
@Data
@EqualsAndHashCode
@ToString
public class CommitEntity {
  private Long id;
  private String name;
  private Long timestamp;
  private String comment;
  private String author;
  private boolean analyzed = false;

  @Relationship(type = "IS_CHILD_OF")
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private List<CommitEntity> parents = new ArrayList<>();

  @Relationship(direction = Relationship.INCOMING, type = "CHANGED_IN")
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private List<FileToCommitRelationshipEntity> touchedFiles = new ArrayList<>();
}
