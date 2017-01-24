package org.wickedsource.coderadar.filepattern.domain;

import javax.persistence.*;
import org.wickedsource.coderadar.project.domain.InclusionType;
import org.wickedsource.coderadar.project.domain.Project;

/**
 * Contains an Ant-style path pattern to define a certain set of files that is of importance to a
 * coderadar Project.
 */
@Entity
@Table
public class FilePattern {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column private String pattern;

  @Column
  @Enumerated(EnumType.STRING)
  private InclusionType inclusionType;

  @Column
  @Enumerated(EnumType.STRING)
  private FileSetType fileSetType;

  @ManyToOne private Project project;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /** Ant-style file path pattern. */
  public String getPattern() {
    return pattern;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public InclusionType getInclusionType() {
    return inclusionType;
  }

  public void setInclusionType(InclusionType inclusionType) {
    this.inclusionType = inclusionType;
  }

  public FileSetType getFileSetType() {
    return fileSetType;
  }

  public void setFileSetType(FileSetType fileSetType) {
    this.fileSetType = fileSetType;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }
}
