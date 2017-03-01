package org.wickedsource.coderadar.filepattern.domain;

import javax.persistence.*;
import org.wickedsource.coderadar.project.domain.InclusionType;
import org.wickedsource.coderadar.project.domain.Project;

/**
 * Contains an Ant-style path pattern to define a certain set of files that is of importance to a
 * coderadar Project.
 */
@Entity
@Table(name = "file_pattern")
@SequenceGenerator(name = "file_pattern_sequence", sequenceName = "seq_fpat_id", allocationSize = 1)
public class FilePattern {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_pattern_sequence")
  @Column(name = "id")
  private Long id;

  @Column(name = "pattern")
  private String pattern;

  @Column(name = "inclusion_type")
  @Enumerated(EnumType.STRING)
  private InclusionType inclusionType;

  @Column(name = "file_set_type")
  @Enumerated(EnumType.STRING)
  private FileSetType fileSetType;

  @ManyToOne
  @JoinColumn(name = "project_id")
  private Project project;

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
