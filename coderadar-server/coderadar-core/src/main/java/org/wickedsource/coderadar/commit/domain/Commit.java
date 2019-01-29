package org.wickedsource.coderadar.commit.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.Data;
import org.wickedsource.coderadar.project.domain.Project;

/** Metadata about a commit to a Git repository. */
@Entity
@Table(name = "commit")
@EntityListeners(UpdateDateCoordinatesEntityListener.class)
@SequenceGenerator(name = "commit_sequence", sequenceName = "seq_comm_id", allocationSize = 1)
@Data
public class Commit {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commit_sequence")
  @Column(name = "id")
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @ManyToOne(optional = false)
  @JoinColumn(name = "project_id")
  private Project project;

  @Column(name = "timestamp")
  @Temporal(TemporalType.TIMESTAMP)
  private Date timestamp;

  @Column(name = "comment")
  private String comment;

  @Column(name = "author", nullable = false)
  private String author;

  @Column(name = "scanned", nullable = false)
  private boolean scanned = false;

  @Column(name = "merged", nullable = false)
  private boolean merged = false;

  @Column(name = "analyzed", nullable = false)
  private boolean analyzed = false;

  @Column(name = "sequence_number", nullable = false)
  private Integer sequenceNumber;

  @Column(name = "first_parent")
  private String firstParent;

  @OneToMany(mappedBy = "id.commit")
  private Set<CommitToFileAssociation> files = new HashSet<>();

  /**
   * The DateCoordinates contain each date unit (year, month, ...) in a separate field. This allows
   * for easier SQL queries as well as easier computations with dates. Note that this field is set
   * automatically when the timestamp field is set.
   */
  @Embedded private DateCoordinates dateCoordinates;

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
    if (this.dateCoordinates == null) {
      this.dateCoordinates = new DateCoordinates();
    }
  }

  public void setComment(String comment) {
    if (comment.length() > 255) {
      // truncating commit message if too long for database
      this.comment = comment.substring(0, 252) + "...";
    } else {
      this.comment = comment;
    }
  }

  @Override
  public String toString() {
    return String.format(
        "[Commit: projectId=%d, id=%d; name=%s]", this.project.getId(), this.id, this.name);
  }
}
