package io.reflectoring.coderadar.core.analyzer.domain;

import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Date;
import java.util.List;

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
