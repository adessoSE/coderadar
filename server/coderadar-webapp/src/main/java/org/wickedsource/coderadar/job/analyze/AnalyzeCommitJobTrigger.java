package org.wickedsource.coderadar.job.analyze;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.CoderadarConfiguration;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.job.JobLogger;
import org.wickedsource.coderadar.job.core.ProcessingStatus;
import org.wickedsource.coderadar.project.domain.ProjectRepository;

import java.util.Arrays;
import java.util.Date;

@Service
public class AnalyzeCommitJobTrigger {

    private JobLogger jobLogger = new JobLogger();

    private CoderadarConfiguration config;

    private ProjectRepository projectRepository;

    private CommitRepository commitRepository;

    private AnalyzeCommitJobRepository analyzeCommitJobRepository;

    @Autowired
    public AnalyzeCommitJobTrigger(CoderadarConfiguration config, ProjectRepository projectRepository, CommitRepository commitRepository, AnalyzeCommitJobRepository analyzeCommitJobRepository) {
        this.config = config;
        this.projectRepository = projectRepository;
        this.commitRepository = commitRepository;
        this.analyzeCommitJobRepository = analyzeCommitJobRepository;
    }

    @Scheduled(fixedDelay = 5000)
    private void trigger() {
        if (config.isMaster()) {
            for (Commit commit : commitRepository.findCommitsToBeAnalyzed(Arrays.asList(ProcessingStatus.PROCESSING, ProcessingStatus.WAITING))) {
                AnalyzeCommitJob job = new AnalyzeCommitJob();
                job.setQueuedDate(new Date());
                job.setProcessingStatus(ProcessingStatus.WAITING);
                job.setCommitId(commit.getId());
                analyzeCommitJobRepository.save(job);
                jobLogger.queuedNewJob(job, commit.getProject());
            }
        }
    }
}
