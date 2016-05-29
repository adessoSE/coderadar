package org.wickedsource.coderadar.job.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class FileIdentityMergeJob extends Job {

    @Column
    private Long projectId;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
