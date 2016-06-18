package org.wickedsource.coderadar.job.scan.commit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.CoderadarConfiguration;
import org.wickedsource.coderadar.job.JobLogger;
import org.wickedsource.coderadar.job.core.Job;
import org.wickedsource.coderadar.job.core.ProcessingStatus;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;

import java.util.Arrays;
import java.util.Date;

@Service
@ConditionalOnProperty(CoderadarConfiguration.MASTER)
class CommitScannerTrigger {

    private JobLogger jobLogger = new JobLogger();

    private CoderadarConfiguration config;

    private ProjectRepository projectRepository;

    private ScanCommitsJobRepository jobRepository;

    @Autowired
    public CommitScannerTrigger(CoderadarConfiguration config, ProjectRepository projectRepository, ScanCommitsJobRepository jobRepository) {
        this.config = config;
        this.projectRepository = projectRepository;
        this.jobRepository = jobRepository;
    }

    @Scheduled(fixedDelay = CoderadarConfiguration.TIMER_INTERVAL)
    public void trigger() {
        for (Project project : projectRepository.findAll()) {
            if (shouldJobBeQueuedForProject(project)) {
                ScanCommitsJob newJob = new ScanCommitsJob();
                newJob.setProcessingStatus(ProcessingStatus.WAITING);
                newJob.setQueuedDate(new Date());
                newJob.setProjectId(project.getId());
                jobRepository.save(newJob);
                jobLogger.queuedNewJob(newJob, project);
            }
        }
    }

    private boolean shouldJobBeQueuedForProject(Project project) {
        if (isJobCurrentlyQueuedForProject(project)) {
            jobLogger.alreadyQueuedForProject(ScanCommitsJob.class, project);
            return false;
        } else {
            Job lastJob = jobRepository.findTop1ByProcessingStatusAndProjectIdOrderByQueuedDateDesc(ProcessingStatus.PROCESSED, project.getId());
            return lastJob == null || hasIntervalPassedSince(lastJob);
        }
    }

    private boolean isJobCurrentlyQueuedForProject(Project project) {
        int count = jobRepository.countByProcessingStatusInAndProjectId(Arrays.asList(ProcessingStatus.PROCESSING, ProcessingStatus.WAITING), project.getId());
        return count > 0;
    }

    private boolean hasIntervalPassedSince(Job lastJob) {
        long lastRun = lastJob.getQueuedDate().getTime();
        long nextRun = lastRun + config.getScanIntervalInSeconds() * 1000;
        long now = new Date().getTime();

        return now > nextRun;
    }

}
