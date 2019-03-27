package org.wickedsource.coderadar.filepattern.domain;

import javax.persistence.*;
import lombok.Data;
import org.wickedsource.coderadar.project.domain.InclusionType;
import org.wickedsource.coderadar.project.domain.Project;

/**
 * Contains an Ant-style path pattern to define a certain set of files that is of importance to a
 * coderadar Project.
 */
@Entity
@Table(name = "file_pattern")
@SequenceGenerator(name = "file_pattern_sequence", sequenceName = "seq_fpat_id", allocationSize = 1)
@Data
public class FilePattern {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_pattern_sequence")
  @Column(name = "id")
  private Long id;

  /** Ant-style file path pattern. */
  @Column(name = "pattern")
  private String pattern;

  @Column(name = "inclusion_type")
  @Enumerated(EnumType.STRING)
  private InclusionType inclusionType;

  @ManyToOne
  @JoinColumn(name = "project_id")
  private Project project;
}
