package org.wickedsource.coderadar.job.execute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.wickedsource.coderadar.CoderadarConfiguration;
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

    private CoderadarConfiguration config;

    @Autowired
    public JobExecutor(CommitScanner commitScanner, FileScanner fileScanner, LogMerger merger, CommitAnalyzer commitAnalyzer, CoderadarConfiguration config) {
        this.commitScanner = commitScanner;
        this.fileScanner = fileScanner;
        this.merger = merger;
        this.commitAnalyzer = commitAnalyzer;
        this.config = config;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void execute(Job job) {
        if (job instanceof ScanCommitsJob) {
            commitScanner.scan(((ScanCommitsJob) job).getProjectId());
        } else if (job instanceof ScanFilesJob) {
            fileScanner.scan(((ScanFilesJob) job).getCommitId());
        } else if (job instanceof MergeLogJob) {
            merger.merge(((MergeLogJob) job).getProjectId());
        } else if (job instanceof AnalyzeCommitJob) {
            commitAnalyzer.analyzeCommit(((AnalyzeCommitJob) job).getCommitId());
        } else {
            throw new IllegalArgumentException(String.format("unsupported job type %s", job.getClass()));
        }
    }

}
