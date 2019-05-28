package io.reflectoring.coderadar.core.query.domain;

import java.util.Date;
import java.util.List;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

/** Metadata about a commit to a Git repository. */
@NodeEntity
@Data
public class Commit {
  private Long id;
  private String name;
  private Date timestamp; // TODO: A date converter should be used here.
  private String comment;
  private String author;
  private boolean scanned = false;
  private boolean merged = false;
  private boolean analyzed = false;
  private Integer sequenceNumber;
  private String firstParent;

  @Relationship(direction = INCOMING)
  private Project project;

  @Relationship private List<CommitToFileAssociation> touchedFiles;

  public void setComment(String comment) {
    if (comment.length() > 255) {
      // truncating commit message if too long for database
      this.comment = comment.substring(0, 252) + "...";
    } else {
      this.comment = comment;
    }
  }
}
