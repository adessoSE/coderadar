package org.wickedsource.coderadar.analyzingjob.domain;

import java.util.Date;
import javax.persistence.*;
import org.wickedsource.coderadar.project.domain.Project;

/**
 * A job that defines which commits are to be analyzed. Storing an entity of this type in the
 * database automatically triggers analysis of a project's code base.
 */
@Entity
@Table
public class AnalyzingJob {

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
