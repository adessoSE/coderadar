package org.wickedsource.coderadar.job.execute.scan.commit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.CoderadarConfiguration;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.job.JobLogger;
import org.wickedsource.coderadar.job.domain.ProcessingStatus;
import org.wickedsource.coderadar.job.domain.ScanCommitJob;
import org.wickedsource.coderadar.job.domain.ScanCommitJobRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CommitScannerTrigger {

    private JobLogger jobLogger = new JobLogger();

    private CommitRepository commitRepository;

    private ScanCommitJobRepository jobRepository;

    private CoderadarConfiguration config;

    @Autowired
    public CommitScannerTrigger(CommitRepository commitRepository, ScanCommitJobRepository jobRepository, CoderadarConfiguration config) {
        this.commitRepository = commitRepository;
        this.jobRepository = jobRepository;
        this.config = config;
    }

    @Scheduled(fixedDelay = 1000)
    public void trigger() {
        if (config.isMaster()) {
            List<Commit> unscannedCommits = commitRepository.findByScannedFalse();
            for (Commit commit : unscannedCommits) {
                if (isJobCurrentlyQueuedForCommit(commit)) {
                    jobLogger.alreadyQueuedForCommit(ScanCommitJob.class, commit);
                } else {
                    ScanCommitJob job = new ScanCommitJob();
                    job.setCommitId(commit.getId());
                    job.setProcessingStatus(ProcessingStatus.WAITING);
                    job.setQueuedDate(new Date());
                    jobRepository.save(job);
                    jobLogger.queuedNewJob(job, commit.getProject());
                }
            }
        }
    }

    private boolean isJobCurrentlyQueuedForCommit(Commit commit) {
        int count = jobRepository.countByProcessingStatusInAndCommitId(Arrays.asList(ProcessingStatus.WAITING, ProcessingStatus.PROCESSING), commit.getId());
        return count > 0;
    }

}
