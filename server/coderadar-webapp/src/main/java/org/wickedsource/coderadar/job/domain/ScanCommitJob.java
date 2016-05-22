package org.wickedsource.coderadar.job.domain;


import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ScanCommitJob extends Job {

    @Column
    private Long commitId;

    public Long getCommitId() {
        return commitId;
    }

    public void setCommitId(Long commitId) {
        this.commitId = commitId;
    }

}
