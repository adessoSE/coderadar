package org.wickedsource.coderadar.job.core;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import org.wickedsource.coderadar.projectadministration.domain.Project;

/** The Job entity defines a task in the coderadar application that is run asynchronously. */
@Entity
@Table(name = "job")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "jobType", discriminatorType = DiscriminatorType.STRING)
@SequenceGenerator(name = "job_sequence", sequenceName = "seq_job_id", allocationSize = 1)
@Data
public class Job {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_sequence")
  @Column(name = "id")
  private Long id;

  @Column(name = "version")
  @Version
  private Integer version;

  @Column(name = "queued_date", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date queuedDate;

  @Column(name = "start_date")
  @Temporal(TemporalType.TIMESTAMP)
  private Date startDate;

  @Column(name = "end_date")
  @Temporal(TemporalType.TIMESTAMP)
  private Date endDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "processing_status", nullable = false)
  private ProcessingStatus processingStatus;

  @Enumerated(EnumType.STRING)
  @Column(name = "result_status")
  private ResultStatus resultStatus;

  @Column(name = "message")
  private String message;

  @ManyToOne
  @JoinColumn(name = "project_id")
  private Project project;

  @Override
  public String toString() {
    return String.format("[Job: id=%d; type=%s]", this.id, getClass().getSimpleName());
  }
}
