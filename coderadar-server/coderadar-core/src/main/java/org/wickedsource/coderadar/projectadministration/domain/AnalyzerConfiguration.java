package org.wickedsource.coderadar.projectadministration.domain;

import javax.persistence.*;
import lombok.Data;
import org.wickedsource.coderadar.project.domain.Project;

/** An AnalyzerConfiguration stores the configuration for a single analyzer plugin in a project. */
@Entity
@Table(name = "analyzer_configuration")
@SequenceGenerator(
  name = "analyzer_configuration_sequence",
  sequenceName = "seq_acon_id",
  allocationSize = 1
)
@Data
public class AnalyzerConfiguration {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "analyzer_configuration_sequence")
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "project_id")
  private Project project;

  @Column(name = "analyzer_name")
  private String analyzerName;

  @Column(name = "enabled")
  private Boolean enabled;
}
