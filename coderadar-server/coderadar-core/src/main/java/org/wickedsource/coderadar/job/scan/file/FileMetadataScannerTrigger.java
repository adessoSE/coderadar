package org.wickedsource.coderadar.job.scan.file;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.core.configuration.CoderadarConfiguration;
import org.wickedsource.coderadar.filepattern.domain.FilePatternRepository;
import org.wickedsource.coderadar.job.JobLogger;
import org.wickedsource.coderadar.job.core.ProcessingStatus;

@Service
@ConditionalOnProperty("coderadar.master")
public class FileMetadataScannerTrigger {

  private JobLogger jobLogger;

  private CommitRepository commitRepository;

  private ScanFilesJobRepository jobRepository;

  private FilePatternRepository filePatternRepository;

  @Autowired
  public FileMetadataScannerTrigger(
      JobLogger jobLogger,
      CommitRepository commitRepository,
      ScanFilesJobRepository jobRepository,
      FilePatternRepository filePatternRepository) {
    this.jobLogger = jobLogger;
    this.commitRepository = commitRepository;
    this.jobRepository = jobRepository;
    this.filePatternRepository = filePatternRepository;
  }

  @Scheduled(fixedDelay = CoderadarConfiguration.TIMER_INTERVAL)
  public void trigger() {
    List<Commit> unscannedCommits = commitRepository.findByScannedFalse();
    for (Commit commit : unscannedCommits) {
      if (isJobCurrentlyQueuedForCommit(commit)) {
        jobLogger.alreadyQueuedForCommit(ScanFilesJob.class, commit);
      } else if (hasFilePatternsConfigured(commit)) {
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
    int count =
        jobRepository.countByProcessingStatusInAndCommitId(
            Arrays.asList(ProcessingStatus.WAITING, ProcessingStatus.PROCESSING), commit.getId());
    return count > 0;
  }

  private boolean hasFilePatternsConfigured(Commit commit) {
    return filePatternRepository.countByProjectId(commit.getProject().getId()) > 0;
  }
}
