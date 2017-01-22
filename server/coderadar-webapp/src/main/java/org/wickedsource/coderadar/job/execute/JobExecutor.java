package org.wickedsource.coderadar.job.execute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.wickedsource.coderadar.job.analyze.AnalyzeCommitJob;
import org.wickedsource.coderadar.job.analyze.CommitAnalyzer;
import org.wickedsource.coderadar.job.core.Job;
import org.wickedsource.coderadar.job.merge.LogMerger;
import org.wickedsource.coderadar.job.merge.MergeLogJob;
import org.wickedsource.coderadar.job.scan.commit.CommitScanner;
import org.wickedsource.coderadar.job.scan.commit.ScanCommitsJob;
import org.wickedsource.coderadar.job.scan.file.FileScanner;
import org.wickedsource.coderadar.job.scan.file.ScanFilesJob;

@Service
class JobExecutor {

  private CommitScanner commitScanner;

  private FileScanner fileScanner;

  private LogMerger merger;

  private CommitAnalyzer commitAnalyzer;

  @Autowired
  public JobExecutor(
      CommitScanner commitScanner,
      FileScanner fileScanner,
      LogMerger merger,
      CommitAnalyzer commitAnalyzer) {
    this.commitScanner = commitScanner;
    this.fileScanner = fileScanner;
    this.merger = merger;
    this.commitAnalyzer = commitAnalyzer;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void execute(Job job) {
    if (job instanceof ScanCommitsJob) {
      commitScanner.scan(job.getProject());
    } else if (job instanceof ScanFilesJob) {
      fileScanner.scan(((ScanFilesJob) job).getCommit());
    } else if (job instanceof MergeLogJob) {
      merger.merge(job.getProject());
    } else if (job instanceof AnalyzeCommitJob) {
      commitAnalyzer.analyzeCommit(((AnalyzeCommitJob) job).getCommit());
    } else {
      throw new IllegalArgumentException(String.format("unsupported job type %s", job.getClass()));
    }
  }
}
