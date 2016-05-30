package org.wickedsource.coderadar.job.analyze;


import org.wickedsource.coderadar.job.core.Job;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
* A Job that analyzes a single commit.
 */
@Entity
public class AnalyzeCommitJob extends Job {

    @Column
    private Long commitId;

    public Long getCommitId() {
        return commitId;
    }

    public void setCommitId(Long commitId) {
        this.commitId = commitId;
    }

}
