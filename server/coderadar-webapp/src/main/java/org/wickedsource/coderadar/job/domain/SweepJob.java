package org.wickedsource.coderadar.job.domain;

import javax.persistence.Entity;

@Entity
public class SweepJob extends Job {

    private Long commitId;

    public Long getCommitId() {
        return commitId;
    }

    public void setCommitId(Long commitId) {
        this.commitId = commitId;
    }
}
