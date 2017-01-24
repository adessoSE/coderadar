package org.wickedsource.coderadar.job.scan.file;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.job.core.Job;

/**
 * A Job that scans all files of a single Commit to a VCS and stores metadata about them in the
 * database.
 */
@Entity
public class ScanFilesJob extends Job {

  @ManyToOne private Commit commit;

  public Commit getCommit() {
    return commit;
  }

  public void setCommit(Commit commit) {
    this.commit = commit;
  }
}
