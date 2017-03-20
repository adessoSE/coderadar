package org.wickedsource.coderadar.job;

import static com.codahale.metrics.MetricRegistry.name;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.job.core.Job;
import org.wickedsource.coderadar.project.domain.Project;

/**
 * Central logger for logging informations about running and queuing jobs. This way, all log
 * information about jobs can easily be identified and even routed into a separate log file.
 *
 * <p>This class also exposes some metrics about the job processing to Spring Boot's CounterService
 * and GaugeService so that they can be inspected by monitoring tools (by default via the endpoint
 * "/metrics").
 */
@Component
public class JobLogger {

  private Logger logger = LoggerFactory.getLogger(JobLogger.class);

  private Meter failedJobsMeter;

  private Meter jobConflictMeter;

  private Meter finishedJobsMeter;

  @Autowired
  public JobLogger(MetricRegistry metricRegistry) {
    this.failedJobsMeter = metricRegistry.meter(name(JobLogger.class, "failed"));
    this.jobConflictMeter = metricRegistry.meter(name(JobLogger.class, "conflicts"));
    this.finishedJobsMeter = metricRegistry.meter(name(JobLogger.class, "finished"));
  }

  public void queuedNewJob(Job job, Project project) {
    logger.info("queued new job {} for project {}", job, project);
  }

  public void alreadyQueuedForProject(Class<? extends Job> jobClass, Project project) {
    logger.debug(
        "not queueing a new job of type {} since there already is one in queue for project {}",
        jobClass.getSimpleName(),
        project);
  }

  public void alreadyQueuedForCommit(Class<? extends Job> jobClass, Commit commit) {
    logger.debug(
        "not queueing a new job of type {} since there already is one in queue for commit {}",
        jobClass.getSimpleName(),
        commit);
  }

  public void couldNotObtainJob() {
    logger.debug(
        "could not obtain the next job in queue due to contention with another transaction...trying again next time!");
    jobConflictMeter.mark();
  }

  public void emptyQueue() {
    logger.debug("no Jobs waiting in queue. Going back to sleep.");
  }

  public void startingJob(Job job) {
    logger.info("starting to process job: {}", job);
  }

  public void successfullyFinishedJob(Job job) {
    logger.info("successfully processed job: {}", job);
    finishedJobsMeter.mark();
  }

  public void jobFailed(Job job, Throwable cause) {
    logger.error(String.format("job failed due to error: %s", job), cause);
    failedJobsMeter.mark();
  }
}
