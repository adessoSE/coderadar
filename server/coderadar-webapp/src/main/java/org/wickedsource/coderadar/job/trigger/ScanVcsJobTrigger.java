package org.wickedsource.coderadar.job.trigger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.CoderadarConfiguration;
import org.wickedsource.coderadar.job.domain.Job;
import org.wickedsource.coderadar.job.domain.JobRepository;
import org.wickedsource.coderadar.job.domain.ProcessingStatus;
import org.wickedsource.coderadar.job.domain.ScanVcsJob;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;

import java.util.Calendar;
import java.util.Date;

@Service
public class ScanVcsJobTrigger {

    private Logger logger = LoggerFactory.getLogger(ScanVcsJobTrigger.class);

    private CoderadarConfiguration config;

    private ProjectRepository projectRepository;

    private JobRepository jobRepository;

    @Autowired
    public ScanVcsJobTrigger(CoderadarConfiguration config, ProjectRepository projectRepository, JobRepository jobRepository) {
        this.config = config;
        this.projectRepository = projectRepository;
        this.jobRepository = jobRepository;
    }

    @Scheduled(fixedDelay = 5000)
    public void trigger() {
        if (config.isMaster()) {
            for (Project project : projectRepository.findAll()) {
                Job lastJob = jobRepository.findTop1ByProcessingStatusOrderByQueuedDate(ProcessingStatus.PROCESSED);
                if (lastJob == null || isIntervalPassed(lastJob)) {
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

    private boolean isIntervalPassed(Job lastJob) {
        Calendar lastDate = Calendar.getInstance();
        lastDate.setTime(lastJob.getQueuedDate());
        lastDate.add(Calendar.SECOND, config.getScanIntervalInSeconds());
        return lastDate.before(new Date());
    }

}
