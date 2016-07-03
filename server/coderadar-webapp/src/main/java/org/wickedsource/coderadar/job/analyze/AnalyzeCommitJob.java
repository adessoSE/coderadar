package org.wickedsource.coderadar.job.analyze;


import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.job.core.Job;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
* A Job that analyzes a single commit.
 */
@Entity
public class AnalyzeCommitJob extends Job {

    @ManyToOne
    private Commit commit;

    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }

}
