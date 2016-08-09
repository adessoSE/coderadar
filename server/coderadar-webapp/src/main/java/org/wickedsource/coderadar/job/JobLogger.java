package org.wickedsource.coderadar.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.job.core.Job;
import org.wickedsource.coderadar.project.domain.Project;

/**
 * <p> Central logger for logging informations about running and queuing jobs. This way, all log information about jobs
 * can easily be identified and even routed into a separate log file. </p> <p> This class also exposes some metrics
 * about the job processing to Spring Boot's CounterService and GaugeService so that they can be inspected by monitoring
 * tools (by default via the endpoint "/metrics"). </p>
 */
@Component
public class JobLogger {

    private Logger logger = LoggerFactory.getLogger(JobLogger.class);

    private CounterService counterService;

    private GaugeService gaugeService;

    @Autowired
    public JobLogger(CounterService counterService, GaugeService gaugeService) {
        this.counterService = counterService;
        this.gaugeService = gaugeService;
    }

    public void queuedNewJob(Job job, Project project) {
        logger.info("Queued new job {} for project {}", job, project);
    }

    public void alreadyQueuedForProject(Class<? extends Job> jobClass, Project project) {
        logger.debug("Not queueing a new job of type {} since there already is one in queue for project {}",
                     jobClass.getSimpleName(), project);
    }

    public void alreadyQueuedForCommit(Class<? extends Job> jobClass, Commit commit) {
        logger.debug("Not queueing a new job of type {} since there already is one in queue for commit {}",
                     jobClass.getSimpleName(), commit);
    }

    public void couldNotObtainJob() {
        logger.debug(
                "Could not obtain the next job in queue due to contention with another transaction...trying again next time!");
        counterService.increment("coderadar.jobs.jobAcquisitionConflicts");
    }

    public void emptyQueue() {
        logger.debug("No Jobs waiting in queue. Going back to sleep.");
    }

    public void startingJob(Job job) {
        logger.info("Starting to process job: {}", job);
        gaugeService.submit(String.format("coderadar.jobs.%s.inProgress", job.getClass().getSimpleName()), 1);
        gaugeService.submit("coderadar.jobs.currentJobStartTime", System.currentTimeMillis());
    }

    public void successfullyFinishedJob(Job job) {
        logger.info("Successfully processed job: {}", job);
        counterService.increment(String.format("coderadar.jobs.%s.success", job.getClass().getSimpleName()));
        gaugeService.submit(String.format("coderadar.jobs.%s.inProgress", job.getClass().getSimpleName()), 0);
        gaugeService.submit("coderadar.jobs.currentJobStartTime", -1);
    }

    public void jobFailed(Job job, Throwable cause) {
        logger.error(String.format("Job failed due to error: %s", job), cause);
        counterService.increment(String.format("coderadar.jobs.%s.failed", job.getClass().getSimpleName()));
        gaugeService.submit(String.format("coderadar.jobs.%s.inProgress", job.getClass().getSimpleName()), 0);
        gaugeService.submit("coderadar.jobs.currentJobStartTime", -1);
    }

}
