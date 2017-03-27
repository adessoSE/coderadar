package org.wickedsource.coderadar.project.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/** A coderadar project that defines the source of files that are to be analyzed. */
@Entity
@Table(
  name = "project",
  uniqueConstraints = {@UniqueConstraint(columnNames = "name")}
)
@SequenceGenerator(name = "project_sequence", sequenceName = "seq_proj_id", allocationSize = 1)
public class Project {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_sequence")
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Embedded private VcsCoordinates vcsCoordinates;

  @Column(name = "workdir_name", nullable = false)
  private String workdirName;

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

  public VcsCoordinates getVcsCoordinates() {
    return vcsCoordinates;
  }

  public void setVcsCoordinates(VcsCoordinates vcsCoordinates) {
    this.vcsCoordinates = vcsCoordinates;
  }

  @Override
  public String toString() {
    return String.format("[Project: id=%d; name=%s]", this.id, this.name);
  }

  public String getWorkdirName() {
    return workdirName;
  }

  public void setWorkdirName(String workdirName) {
    this.workdirName = workdirName;
  }
}
