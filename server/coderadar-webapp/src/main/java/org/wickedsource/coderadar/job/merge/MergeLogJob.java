package org.wickedsource.coderadar.job.merge;

import org.wickedsource.coderadar.job.core.Job;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * A Job that refines the CommitLogEntries that contain metadata about the files of a VCS and
 * merges renamed files to a single FileIdentity.
 */
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
