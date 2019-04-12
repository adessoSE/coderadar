package org.wickedsource.coderadar.projectadministration.domain;

import javax.persistence.*;
import lombok.Data;

/** A coderadar project that defines the source of files that are to be analyzed. */
@Entity
@Table(
  name = "project",
  uniqueConstraints = {@UniqueConstraint(columnNames = "name")}
)
@SequenceGenerator(name = "project_sequence", sequenceName = "seq_proj_id", allocationSize = 1)
@Data
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

  @Override
  public String toString() {
    return String.format("[Project: id=%d; name=%s]", this.id, this.name);
  }
}
