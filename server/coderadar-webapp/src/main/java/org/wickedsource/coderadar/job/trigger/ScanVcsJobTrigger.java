package org.wickedsource.coderadar.job.trigger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.CoderadarConfiguration;
import org.wickedsource.coderadar.job.domain.Job;
import org.wickedsource.coderadar.job.domain.ProcessingStatus;
import org.wickedsource.coderadar.job.domain.ScanVcsJob;
import org.wickedsource.coderadar.job.domain.ScanVcsJobRepository;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;

import java.util.Arrays;
import java.util.Date;

@Service
class ScanVcsJobTrigger {

    private Logger logger = LoggerFactory.getLogger(ScanVcsJobTrigger.class);

    private CoderadarConfiguration config;

    private ProjectRepository projectRepository;

    private ScanVcsJobRepository jobRepository;

    @Autowired
    public ScanVcsJobTrigger(CoderadarConfiguration config, ProjectRepository projectRepository, ScanVcsJobRepository jobRepository) {
        this.config = config;
        this.projectRepository = projectRepository;
        this.jobRepository = jobRepository;
    }

    @Scheduled(fixedDelay = 5000)
    public void trigger() {
        if (config.isMaster()) {
            for (Project project : projectRepository.findAll()) {
                if (shouldJobBeQueuedForProject(project)) {
                    ScanVcsJob newJob = new ScanVcsJob();
                    newJob.setProcessingStatus(ProcessingStatus.WAITING);
                    newJob.setQueuedDate(new Date());
                    newJob.setProjectId(project.getId());
                    jobRepository.save(newJob);
                    logger.info("queued new VCS scanning job for project {}", project.getId());
                }
            }
        }
    }

    private boolean isJobCurrentlyQueuedForProject(Project project) {
        int count = jobRepository.countByProcessingStatusInAndProjectId(Arrays.asList(ProcessingStatus.PROCESSING, ProcessingStatus.WAITING), project.getId());
        return count > 0;
    }

    private boolean shouldJobBeQueuedForProject(Project project){
        if (isJobCurrentlyQueuedForProject(project)){
            logger.info("there is already a VCS scanning job queued for project {}. not queueing another job.");
            return false;
        }else{
            Job lastJob = jobRepository.findTop1ByProcessingStatusAndProjectIdOrderByQueuedDateDesc(ProcessingStatus.PROCESSED, project.getId());
            return lastJob == null || hasIntervalPassedSince(lastJob);
        }
    }

    private boolean hasIntervalPassedSince(Job lastJob) {
        long lastRun = lastJob.getQueuedDate().getTime();
        long nextRun = lastRun + config.getScanIntervalInSeconds() * 1000;
        long now = new Date().getTime();

        return now > nextRun;
    }

}
