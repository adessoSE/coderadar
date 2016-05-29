package org.wickedsource.coderadar.job.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="jobType", discriminatorType = DiscriminatorType.STRING)
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @Version
    private Integer version;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date queuedDate;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProcessingStatus processingStatus;

    @Enumerated(EnumType.STRING)
    @Column
    private ResultStatus resultStatus;

    @Column
    private String message;

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

}
