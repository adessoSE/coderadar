package org.wickedsource.coderadar.analyzingstrategy.domain;

import java.util.Date;
import javax.persistence.*;
import org.wickedsource.coderadar.project.domain.Project;

/** Defines which commits of a project are to be analyzed. */
@Entity
@Table
public class AnalyzingStrategy {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne private Project project;

  @Column private Date fromDate;

  @Column private boolean active = false;

  /**
   * The date from which to start scanning commits. If null, all commits from the very beginning are
   * analyzed.
   */
  public Date getFromDate() {
    return fromDate;
  }

  public void setFromDate(Date fromDate) {
    this.fromDate = fromDate;
  }

  /**
   * If set to false, no new commits will be analyzed for the project. Analyses that are already
   * queued will be performed, however.
   */
  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

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
}
