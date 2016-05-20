package org.wickedsource.coderadar.job.execute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.job.domain.Job;
import org.wickedsource.coderadar.job.domain.ScanVcsJob;
import org.wickedsource.coderadar.job.domain.SweepJob;
import org.wickedsource.coderadar.job.scan.VcsScanner;
import org.wickedsource.coderadar.job.sweep.CommitSweeper;

@Service
class JobExecutor {

    private VcsScanner vcsScanner;

    private CommitSweeper sweeper;

    @Autowired
    public JobExecutor(VcsScanner vcsScanner, CommitSweeper sweeper) {
        this.vcsScanner = vcsScanner;
        this.sweeper = sweeper;
    }

    void execute(Job job) {
        if (job instanceof ScanVcsJob) {
            vcsScanner.scanVcs(((ScanVcsJob) job).getProjectId());
        } else if (job instanceof SweepJob) {
            sweeper.sweepCommit(((SweepJob) job).getCommitId());
        } else {
            throw new IllegalArgumentException(String.format("unsupported job type %s", job.getClass()));
        }
    }

}
