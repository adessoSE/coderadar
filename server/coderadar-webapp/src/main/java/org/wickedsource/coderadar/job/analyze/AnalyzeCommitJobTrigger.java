package org.wickedsource.coderadar.job.analyze;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.CoderadarConfiguration;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.job.JobLogger;
import org.wickedsource.coderadar.project.domain.ProjectRepository;

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
//        if (config.isMaster()) {
//            for (Project project : projectRepository.findAll()) {
//                // TODO: check project's sweep strategy instead of just sweeping ALL commits
//                for (Commit commit : commitRepository.findByProjectIdAndAnalyzedFalseOrderByTimestamp(project.getId())) {
//                    if (analyzeCommitJobRepository.countByCommitIdAndProcessingStatus(commit.getId(), ProcessingStatus.WAITING) == 0) {
//                        AnalyzeCommitJob job = new AnalyzeCommitJob();
//                        job.setQueuedDate(new Date());
//                        job.setProcessingStatus(ProcessingStatus.WAITING);
//                        job.setCommitId(commit.getId());
//                        analyzeCommitJobRepository.save(job);
//                        jobLogger.queuedNewJob(job, project);
//                    }
//                }
//            }
//        }
    }
}
