package org.wickedsource.coderadar.job.core;

import java.util.Date;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.wickedsource.coderadar.project.domain.Project;

/** The Job entity defines a task in the coderadar application that is run asynchronously. */
@Entity
@Table(name = "job")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "jobType", discriminatorType = DiscriminatorType.STRING)
@SequenceGenerator(name = "job_sequence", sequenceName = "seq_job_id")
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getQueuedDate() {
    return queuedDate;
  }

  public void setQueuedDate(Date queuedDate) {
    this.queuedDate = queuedDate;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public ProcessingStatus getProcessingStatus() {
    return processingStatus;
  }

  public void setProcessingStatus(ProcessingStatus processingStatus) {
    this.processingStatus = processingStatus;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ResultStatus getResultStatus() {
    return resultStatus;
  }

  public void setResultStatus(ResultStatus resultStatus) {
    this.resultStatus = resultStatus;
  }

  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  @Override
  public String toString() {
    return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }
}
