package org.wickedsource.coderadar.analyzer.domain;

import javax.persistence.*;
import org.wickedsource.coderadar.project.domain.Project;

/** An AnalyzerConfiguration stores the configuration for a single analyzer plugin in a project. */
@Entity
@Table
public class AnalyzerConfiguration {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne private Project project;

  @Column private String analyzerName;

  @Column private Boolean enabled;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public String getAnalyzerName() {
    return analyzerName;
  }

  public void setAnalyzerName(String analyzerName) {
    this.analyzerName = analyzerName;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }
}
