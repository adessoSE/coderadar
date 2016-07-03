package org.wickedsource.coderadar.factories;

import org.wickedsource.coderadar.job.core.ProcessingStatus;
import org.wickedsource.coderadar.job.scan.commit.ScanCommitsJob;

import java.util.Date;

public class JobFactory {

    public ScanCommitsJob waitingPullJob() {
        ScanCommitsJob job = new ScanCommitsJob();
        job.setId(1L);
        job.setProject(Factories.project().validProject());
        job.setEndDate(null);
        job.setMessage(null);
        job.setProcessingStatus(ProcessingStatus.WAITING);
        job.setQueuedDate(new Date());
        job.setResultStatus(null);
        job.setStartDate(null);
        return job;
    }
}
