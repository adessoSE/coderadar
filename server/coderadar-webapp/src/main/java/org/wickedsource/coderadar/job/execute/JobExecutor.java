package org.wickedsource.coderadar.job.execute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.job.domain.Job;
import org.wickedsource.coderadar.job.domain.ScanVcsJob;
import org.wickedsource.coderadar.job.execute.scan.VcsScanner;

@Service
class JobExecutor {

    private VcsScanner vcsScanner;

    @Autowired
    public JobExecutor(VcsScanner vcsScanner) {
        this.vcsScanner = vcsScanner;
    }

    void execute(Job job) {
        if (job instanceof ScanVcsJob) {
            vcsScanner.scanVcs(((ScanVcsJob) job).getProjectId());
        } else {
            throw new IllegalArgumentException(String.format("unsupported job type %s", job.getClass()));
        }
    }

}
