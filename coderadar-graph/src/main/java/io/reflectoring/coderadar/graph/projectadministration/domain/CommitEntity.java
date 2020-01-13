package io.reflectoring.coderadar.graph.projectadministration.domain;

import java.util.Collections;
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
  private long timestamp;
  private String comment;
  private String author;
  private boolean analyzed = false;

  @Relationship(type = "IS_CHILD_OF")
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private List<CommitEntity> parents = Collections.emptyList();

  @Relationship(direction = Relationship.INCOMING, type = "CHANGED_IN")
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private List<FileToCommitRelationshipEntity> touchedFiles = Collections.emptyList();
}
