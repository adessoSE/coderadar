package org.wickedsource.coderadar.commit.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.wickedsource.coderadar.project.domain.Project;

/** Metadata about a commit to a Git repository. */
@Entity
@Table
@EntityListeners(UpdateDateCoordinatesEntityListener.class)
public class Commit {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private String name;

  @ManyToOne(optional = false)
  private Project project;

  @Column
  @Temporal(TemporalType.TIMESTAMP)
  private Date timestamp;

  @Column private String comment;

  @Column(nullable = false)
  private String author;

  @Column(nullable = false)
  private boolean scanned = false;

  @Column(nullable = false)
  private boolean merged = false;

  @Column(nullable = false)
  private boolean analyzed = false;

  @Column(nullable = false)
  private Integer sequenceNumber;

  @Column private String firstParent;

  @OneToMany(mappedBy = "id.commit")
  private Set<CommitToFileAssociation> files = new HashSet<>();

  @Embedded private DateCoordinates dateCoordinates;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
    if (this.dateCoordinates == null) {
      this.dateCoordinates = new DateCoordinates();
    }
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    if(comment.length() > 255){
      // truncating commit message if too long for database
      this.comment = comment.substring(0, 252) + "...";
    }else {
      this.comment = comment;
    }
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public boolean isScanned() {
    return scanned;
  }

  public void setScanned(boolean scanned) {
    this.scanned = scanned;
  }

  public boolean isMerged() {
    return merged;
  }

  public void setMerged(boolean merged) {
    this.merged = merged;
  }

  public boolean isAnalyzed() {
    return analyzed;
  }

  public void setAnalyzed(boolean analyzed) {
    this.analyzed = analyzed;
  }

  @Override
  public String toString() {
    ReflectionToStringBuilder builder =
        new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
    builder.setExcludeFieldNames("project", "files");
    return builder.build();
  }

  public Set<CommitToFileAssociation> getFiles() {
    return files;
  }

  public void setFiles(Set<CommitToFileAssociation> files) {
    this.files = files;
  }

  public Integer getSequenceNumber() {
    return sequenceNumber;
  }

  public void setSequenceNumber(Integer sequenceNumber) {
    this.sequenceNumber = sequenceNumber;
  }

  /**
   * The DateCoordinates contain each date unit (year, month, ...) in a separate field. This allows
   * for easier SQL queries as well as easier computations with dates. Note that this field is set
   * automatically when the timestamp field is set.
   */
  public DateCoordinates getDateCoordinates() {
    return dateCoordinates;
  }

  void setDateCoordinates(DateCoordinates dateCoordinates) {
    this.dateCoordinates = dateCoordinates;
  }

  public String getFirstParent() {
    return firstParent;
  }

  public void setFirstParent(String firstParent) {
    this.firstParent = firstParent;
  }
}
