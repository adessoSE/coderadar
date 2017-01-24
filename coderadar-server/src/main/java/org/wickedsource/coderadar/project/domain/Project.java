package org.wickedsource.coderadar.project.domain;

import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/** A coderadar project that defines the source of files that are to be analyzed. */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class Project {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Embedded private VcsCoordinates vcsCoordinates;

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
    ReflectionToStringBuilder builder =
        new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
    builder.setExcludeFieldNames("sourceFilePatterns");
    return builder.toString();
  }
}
