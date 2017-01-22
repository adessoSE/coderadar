package org.wickedsource.coderadar.job.analyze;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.job.core.Job;

/** A Job that analyzes a single commit. */
@Entity
public class AnalyzeCommitJob extends Job {

  @ManyToOne private Commit commit;

  public Commit getCommit() {
    return commit;
  }

  public void setCommit(Commit commit) {
    this.commit = commit;
  }
}
