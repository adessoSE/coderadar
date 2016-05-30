package org.wickedsource.coderadar.job.scan.commit;

import org.wickedsource.coderadar.job.core.Job;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ScanCommitsJob extends Job {

    @Column
    private Long projectId;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
