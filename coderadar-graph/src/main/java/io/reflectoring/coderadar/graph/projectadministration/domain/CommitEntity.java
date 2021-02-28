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
  private long timestamp;
  private long hash;
  private boolean analyzed;
  private String comment;
  private String author;
  private String authorEmail;

  @Relationship(type = "IS_CHILD_OF")
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private List<CommitEntity> parents = Collections.emptyList();

  @Relationship(direction = Relationship.INCOMING, type = "CHANGED_IN")
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private List<FileEntity> changedFiles = Collections.emptyList();

  @Relationship(direction = Relationship.INCOMING, type = "DELETED_IN")
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private List<FileEntity> deletedFiles = Collections.emptyList();
}
