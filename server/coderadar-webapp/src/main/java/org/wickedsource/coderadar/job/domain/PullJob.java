package org.wickedsource.coderadar.job.domain;

import javax.persistence.Entity;

@Entity
public class PullJob extends Job {

    private Long projectId;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
