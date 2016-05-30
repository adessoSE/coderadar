package org.wickedsource.coderadar.job.merge;

import org.wickedsource.coderadar.job.core.Job;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class MergeLogJob extends Job {

    @Column
    private Long projectId;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
