package org.wickedsource.coderadar.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wickedsource.coderadar.job.domain.Job;
import org.wickedsource.coderadar.project.domain.Project;

/**
 * Central logger for logging informations about running and queuing jobs. This way, all log information
 * about jobs can easily be identified and even routed into a separate log file.
 */
public class JobLogger {

    private Logger logger = LoggerFactory.getLogger(JobLogger.class);

    public void queuedNewJob(Job job, Project project){
        logger.info("Queued new job {} for project {}", job, project);
    }

    public void alreadyQueued(Class<? extends Job> jobClass, Project project){
        logger.debug("Not queueing a new job of type {} since there already is one in queue for project {}", jobClass.getSimpleName(), project);
    }

    public void couldNotObtainJob(){
        logger.debug("Could not obtain the next job in queue due to contention with another transaction...trying again next time!");
    }

    public void emptyQueue(){
        logger.debug("No Jobs waiting in queue. Going back to sleep.");
    }

    public void startingJob(Job job){
        logger.info("Starting to process job: {}", job);
    }

    public void successfullyFinishedJob(Job job){
        logger.info("Successfully processed job: {}", job);
    }

    public void jobFailed(Job job, Throwable cause){
        logger.error(String.format("Error while processing job: %s", job), cause);
    }


}
