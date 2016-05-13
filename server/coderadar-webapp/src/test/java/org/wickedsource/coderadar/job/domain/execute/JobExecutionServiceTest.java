package org.wickedsource.coderadar.job.domain.execute;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.wickedsource.coderadar.IntegrationTestTemplate;
import org.wickedsource.coderadar.factories.Factories;
import org.wickedsource.coderadar.job.domain.Job;
import org.wickedsource.coderadar.job.domain.JobRepository;
import org.wickedsource.coderadar.job.domain.ProcessingStatus;
import org.wickedsource.coderadar.job.domain.ResultStatus;
import org.wickedsource.coderadar.job.domain.queue.JobQueueService;
import org.wickedsource.coderadar.job.domain.queue.JobUpdater;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;

@DirtiesContext
public class JobExecutionServiceTest extends IntegrationTestTemplate {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobQueueService jobQueueService;

    @Autowired
    private JobUpdater jobUpdater;

    @Mock
    private JobExecutor jobExecutor = new JobExecutor();


    @Test
    public void testSuccessfulExecution() {
        Job jobBeforeExecution = initJob();

        JobExecutionService executionService = new JobExecutionService(jobQueueService, jobUpdater, jobExecutor, jobRepository);
        executionService.executeNextJobInQueue();

        Job jobAfterExecution = jobRepository.findOne(jobBeforeExecution.getId());
        Assert.assertEquals(ResultStatus.SUCCESS, jobAfterExecution.getResultStatus());
        Assert.assertEquals(ProcessingStatus.PROCESSED, jobAfterExecution.getProcessingStatus());
    }

    private Job initJob() {
        Job jobBeforeExecution = Factories.job().waitingPullJob();
        jobBeforeExecution.setId(null);
        jobBeforeExecution = jobRepository.save(jobBeforeExecution);
        Assert.assertEquals(null, jobBeforeExecution.getResultStatus());
        Assert.assertEquals(ProcessingStatus.WAITING, jobBeforeExecution.getProcessingStatus());
        return jobBeforeExecution;
    }

    @Test
    public void testFailedExecution() {
        Job jobBeforeExecution = initJob();

        doThrow(new RuntimeException("bwaaah")).when(jobExecutor).execute(any(Job.class));

        JobExecutionService executionService = new JobExecutionService(jobQueueService, jobUpdater, jobExecutor, jobRepository);
        executionService.executeNextJobInQueue();

        Job jobAfterExecution = jobRepository.findOne(jobBeforeExecution.getId());
        Assert.assertEquals(ResultStatus.FAILED, jobAfterExecution.getResultStatus());
        Assert.assertEquals(ProcessingStatus.PROCESSED, jobAfterExecution.getProcessingStatus());
    }


}