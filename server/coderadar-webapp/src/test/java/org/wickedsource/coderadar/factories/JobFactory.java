package org.wickedsource.coderadar.factories;

import org.wickedsource.coderadar.job.domain.ProcessingStatus;
import org.wickedsource.coderadar.job.domain.ScanVcsJob;

import java.util.Date;

public class JobFactory {

    public ScanVcsJob waitingPullJob() {
        ScanVcsJob job = new ScanVcsJob();
        job.setId(1L);
        job.setProjectId(1L);
        job.setEndDate(null);
        job.setMessage(null);
        job.setProcessingStatus(ProcessingStatus.WAITING);
        job.setQueuedDate(new Date());
        job.setResultStatus(null);
        job.setStartDate(null);
        return job;
    }
}
