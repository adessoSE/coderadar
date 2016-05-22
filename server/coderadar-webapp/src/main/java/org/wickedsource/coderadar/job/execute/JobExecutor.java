package org.wickedsource.coderadar.job.execute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.CoderadarConfiguration;
import org.wickedsource.coderadar.job.domain.Job;
import org.wickedsource.coderadar.job.domain.ScanVcsJob;
import org.wickedsource.coderadar.job.domain.SweepJob;
import org.wickedsource.coderadar.job.scan.VcsRepositoryScanner;
import org.wickedsource.coderadar.job.sweep.CommitSweeper;

@Service
class JobExecutor {

    private VcsRepositoryScanner vcsScanner;

    private CommitSweeper sweeper;

    private CoderadarConfiguration config;

    @Autowired
    public JobExecutor(VcsRepositoryScanner vcsScanner, CommitSweeper sweeper, CoderadarConfiguration config) {
        this.vcsScanner = vcsScanner;
        this.sweeper = sweeper;
        this.config = config;
    }

    void execute(Job job) {
        if (config.isSlave()) {
            if (job instanceof ScanVcsJob) {
                vcsScanner.scan(((ScanVcsJob) job).getProjectId());
            } else if (job instanceof SweepJob) {
                sweeper.sweepCommit(((SweepJob) job).getCommitId());
            } else {
                throw new IllegalArgumentException(String.format("unsupported job type %s", job.getClass()));
            }
        }
    }

}
