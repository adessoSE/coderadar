package org.wickedsource.coderadar.job.scan.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.CoderadarConfiguration;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.job.JobLogger;
import org.wickedsource.coderadar.job.core.ProcessingStatus;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@ConditionalOnProperty(CoderadarConfiguration.MASTER)
public class FileScannerTrigger {

    private JobLogger jobLogger = new JobLogger();

    private CommitRepository commitRepository;

    private ScanFilesJobRepository jobRepository;

    private CoderadarConfiguration config;

    @Autowired
    public FileScannerTrigger(CommitRepository commitRepository, ScanFilesJobRepository jobRepository, CoderadarConfiguration config) {
        this.commitRepository = commitRepository;
        this.jobRepository = jobRepository;
        this.config = config;
    }

    @Scheduled(fixedDelay = CoderadarConfiguration.TIMER_INTERVAL)
    public void trigger() {
        List<Commit> unscannedCommits = commitRepository.findByScannedFalse();
        for (Commit commit : unscannedCommits) {
            if (isJobCurrentlyQueuedForCommit(commit)) {
                jobLogger.alreadyQueuedForCommit(ScanFilesJob.class, commit);
            } else {
                ScanFilesJob job = new ScanFilesJob();
                job.setCommit(commit);
                job.setProject(commit.getProject());
                job.setProcessingStatus(ProcessingStatus.WAITING);
                job.setQueuedDate(new Date());
                jobRepository.save(job);
                jobLogger.queuedNewJob(job, commit.getProject());
            }
        }
    }

    private boolean isJobCurrentlyQueuedForCommit(Commit commit) {
        int count = jobRepository.countByProcessingStatusInAndCommitId(Arrays.asList(ProcessingStatus.WAITING, ProcessingStatus.PROCESSING), commit.getId());
        return count > 0;
    }

}
