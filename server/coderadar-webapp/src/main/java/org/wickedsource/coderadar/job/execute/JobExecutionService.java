package org.wickedsource.coderadar.job.execute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wickedsource.coderadar.job.domain.Job;
import org.wickedsource.coderadar.job.domain.JobRepository;
import org.wickedsource.coderadar.job.domain.ProcessingStatus;
import org.wickedsource.coderadar.job.domain.ResultStatus;
import org.wickedsource.coderadar.job.queue.JobQueueService;
import org.wickedsource.coderadar.job.queue.JobUpdater;

import java.util.Date;

@Service
@Transactional
class JobExecutionService {

    private Logger logger = LoggerFactory.getLogger(JobExecutionService.class);

    private JobQueueService queueService;

    private JobUpdater jobUpdater;

    private JobExecutor executor;

    private JobRepository jobRepository;

    @Autowired
    public JobExecutionService(JobQueueService queueService, JobUpdater jobUpdater, JobExecutor executor, JobRepository jobRepository) {
        this.queueService = queueService;
        this.jobUpdater = jobUpdater;
        this.executor = executor;
        this.jobRepository = jobRepository;
    }

    /**
     * Loads the next job from the queue and passes it to the executor to process it. The job will be marked as
     * successfully processed after execution. If an exception occurs, the job will be marked as failed but processed.
     */
    @Scheduled(fixedDelay = 5000)
    public void executeNextJobInQueue() {
        if(queueService.isQueueEmpty()){
            logger.info("no jobs in queue...laying back and enjoying the sun!");
            return;
        }
        Job job = queueService.dequeue();
        if (job == null) {
            logger.info("could not obtain the next job in queue due to contention with another transaction...trying again next time!");
        } else {
            logger.info("starting to process job {} of type {}", job.getId(), job.getClass());
            try {
                executor.execute(job);
                job.setEndDate(new Date());
                job.setProcessingStatus(ProcessingStatus.PROCESSED);
                job.setResultStatus(ResultStatus.SUCCESS);
                jobRepository.save(job);
                logger.info("successfully processed job {} of type {}", job.getId(), job.getClass());
            } catch (Exception e) {
                job.setResultStatus(ResultStatus.FAILED);
                job.setProcessingStatus(ProcessingStatus.PROCESSED);
                job.setEndDate(new Date());
                job.setMessage(String.format("Failed due to exception of type %s. View the log file for details.", e.getClass()));
                // storing the failed job back into the database in a separate transaction because the current
                // transaction may be marked for rollback
                jobUpdater.updateJob(job);
                logger.error(String.format("error while processing job with id %d. marking job as FAILED", job.getId()), e);
            }
        }
    }
}
