package org.wickedsource.coderadar.job.execute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.job.domain.Job;
import org.wickedsource.coderadar.job.domain.ScanVcsJob;
import org.wickedsource.coderadar.job.execute.scan.ScanVcsJobExecutor;

@Service
class JobExecutor {

    private ScanVcsJobExecutor scanVcsJobExecutor;

    @Autowired
    public JobExecutor(ScanVcsJobExecutor scanVcsJobExecutor) {
        this.scanVcsJobExecutor = scanVcsJobExecutor;
    }

    void execute(Job job) {
        if (job instanceof ScanVcsJob) {
            scanVcsJobExecutor.scanVcs(((ScanVcsJob) job).getProjectId());
        } else {
            throw new IllegalArgumentException(String.format("unsupported job type %s", job.getClass()));
        }
    }

}
