package org.wickedsource.coderadar.job.analyze;

import java.util.Arrays;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfigurationRepository;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.core.configuration.CoderadarConfiguration;
import org.wickedsource.coderadar.filepattern.domain.FilePatternRepository;
import org.wickedsource.coderadar.job.JobLogger;
import org.wickedsource.coderadar.job.core.ProcessingStatus;

@Service
@ConditionalOnProperty("coderadar.master")
public class AnalyzeCommitJobTrigger {

  private Logger logger = LoggerFactory.getLogger(AnalyzeCommitJobTrigger.class);

  private JobLogger jobLogger;

  private CommitRepository commitRepository;

  private AnalyzeCommitJobRepository analyzeCommitJobRepository;

  private FilePatternRepository filePatternRepository;

  private AnalyzerConfigurationRepository analyzerConfigurationRepository;

  @Autowired
  public AnalyzeCommitJobTrigger(
      JobLogger jobLogger,
      CommitRepository commitRepository,
      AnalyzeCommitJobRepository analyzeCommitJobRepository,
      FilePatternRepository filePatternRepository,
      AnalyzerConfigurationRepository analyzerConfigurationRepository) {
    this.jobLogger = jobLogger;
    this.commitRepository = commitRepository;
    this.analyzeCommitJobRepository = analyzeCommitJobRepository;
    this.filePatternRepository = filePatternRepository;
    this.analyzerConfigurationRepository = analyzerConfigurationRepository;
  }

  @Scheduled(fixedDelay = CoderadarConfiguration.TIMER_INTERVAL)
  public void trigger() {
    for (Commit commit :
        commitRepository.findCommitsToBeAnalyzed(
            Arrays.asList(ProcessingStatus.PROCESSING, ProcessingStatus.WAITING))) {

      if (!hasFilePatternsConfigured(commit)) {
        logger.debug(
            "skipping commit {} because no file patterns are configured for project {}",
            commit.getName(),
            commit.getProject().getId());
        continue;
      }

      if (!hasAnalyzerConfigured(commit)) {
        logger.debug(
            "skipping commit {} because no analyzers are configured for project {}",
            commit.getName(),
            commit.getProject().getId());
        continue;
      }

      AnalyzeCommitJob job = new AnalyzeCommitJob();
      job.setQueuedDate(new Date());
      job.setProcessingStatus(ProcessingStatus.WAITING);
      job.setCommit(commit);
      job.setProject(commit.getProject());
      analyzeCommitJobRepository.save(job);
      jobLogger.queuedNewJob(job, commit.getProject());
    }
  }

  private boolean hasFilePatternsConfigured(Commit commit) {
    return filePatternRepository.countByProjectId(commit.getProject().getId()) > 0;
  }

  private boolean hasAnalyzerConfigured(Commit commit) {
    return analyzerConfigurationRepository.countByProjectId(commit.getProject().getId()) > 0;
  }
}
