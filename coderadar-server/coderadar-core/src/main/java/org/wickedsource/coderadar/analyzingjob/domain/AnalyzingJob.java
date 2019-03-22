package org.wickedsource.coderadar.analyzingjob.domain;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import org.wickedsource.coderadar.projectadministration.domain.Project;

/**
 * A job that defines which commits are to be analyzed. Storing an entity of this type in the
 * database automatically triggers analysis of a project's code base.
 */
@Entity
@Table(name = "analyzing_job")
@SequenceGenerator(
  name = "analyzing_job_sequence",
  sequenceName = "seq_ajob_id",
  allocationSize = 1
)
@Data
public class AnalyzingJob {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "analyzing_job_sequence")
  @Column(name = "id")
  private Long id;

  @OneToOne
  @JoinColumn(name = "project_id")
  private Project project;

  /**
   * The date from which to start scanning commits. If null, all commits from the very beginning are
   * analyzed.
   */
  @Column(name = "from_date")
  private Date fromDate;

  /**
   * If set to false, no new commits will be analyzed for the project. Analyses that are already
   * queued will be performed, however.
   */
  @Column(name = "active")
  private boolean active = false;
}
