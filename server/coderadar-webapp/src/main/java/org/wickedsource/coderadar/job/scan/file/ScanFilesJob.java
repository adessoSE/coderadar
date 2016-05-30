package org.wickedsource.coderadar.job.scan.file;


import org.wickedsource.coderadar.job.core.Job;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ScanFilesJob extends Job {

    @Column
    private Long commitId;

    public Long getCommitId() {
        return commitId;
    }

    public void setCommitId(Long commitId) {
        this.commitId = commitId;
    }

}
