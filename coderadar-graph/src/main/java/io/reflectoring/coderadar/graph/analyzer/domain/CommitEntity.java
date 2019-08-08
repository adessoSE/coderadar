package io.reflectoring.coderadar.graph.analyzer.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/** Metadata about a commit to a Git repository. */
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
  private List<CommitEntity> parents = new ArrayList<>();

  @Relationship(direction = Relationship.INCOMING, type = "CHANGED_IN")
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private List<FileToCommitRelationshipEntity> touchedFiles = new LinkedList<>();

  @Relationship(direction = Relationship.INCOMING, type = "VALID_FOR")
  @EqualsAndHashCode.Exclude
  private List<MetricValueEntity> metricValues = new LinkedList<>();

  public void setComment(String comment) {
    if (comment.length() > 255) {
      // truncating commit message if too long for database
      this.comment = comment.substring(0, 252) + "...";
    } else {
      this.comment = comment;
    }
  }
}
