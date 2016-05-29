package org.wickedsource.coderadar.job.execute.merge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.CoderadarConfiguration;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.job.JobLogger;
import org.wickedsource.coderadar.job.domain.FileIdentityMergeJob;
import org.wickedsource.coderadar.job.domain.FileIdentityMergeJobRepository;
import org.wickedsource.coderadar.job.domain.ProcessingStatus;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;

import java.util.Arrays;
import java.util.Date;

@Service
public class FileIdentityMergerTrigger {

    private JobLogger jobLogger = new JobLogger();

    private CoderadarConfiguration config;

    private FileIdentityMergeJobRepository jobRepository;

    private ProjectRepository projectRepository;

    private CommitRepository commitRepository;

    @Autowired
    public FileIdentityMergerTrigger(CoderadarConfiguration config, FileIdentityMergeJobRepository jobRepository, ProjectRepository projectRepository, CommitRepository commitRepository) {
        this.config = config;
        this.jobRepository = jobRepository;
        this.projectRepository = projectRepository;
        this.commitRepository = commitRepository;
    }

    @Scheduled(fixedDelay = CoderadarConfiguration.TIMER_INTERVAL)
    public void trigger() {
        if (config.isMaster()) {
            for (Project project : projectRepository.findAll()) {
                if (shouldJobBeQueuedForProject(project)) {
                    FileIdentityMergeJob newJob = new FileIdentityMergeJob();
                    newJob.setProcessingStatus(ProcessingStatus.WAITING);
                    newJob.setQueuedDate(new Date());
                    newJob.setProjectId(project.getId());
                    jobRepository.save(newJob);
                    jobLogger.queuedNewJob(newJob, project);
                }
            }
        }
    }

    private boolean shouldJobBeQueuedForProject(Project project) {
        if (isJobCurrentlyQueuedForProject(project)) {
            jobLogger.alreadyQueuedForProject(FileIdentityMergeJob.class, project);
            return false;
        } else {
            int scannedAndUnmergedCommits = commitRepository.countByProjectIdAndScannedTrueAndMergedFalse(project.getId());
            int totalCommits = commitRepository.countByProjectId(project.getId());
            return totalCommits == scannedAndUnmergedCommits && totalCommits > 0;
        }
    }

    private boolean isJobCurrentlyQueuedForProject(Project project) {
        int count = jobRepository.countByProcessingStatusInAndProjectId(Arrays.asList(ProcessingStatus.WAITING, ProcessingStatus.PROCESSING), project.getId());
        return count > 0;
    }
}
