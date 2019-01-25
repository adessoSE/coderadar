package org.wickedsource.coderadar.job.execute;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.Jobs.SINGLE_PROJECT_WITH_WAITING_JOB;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.wickedsource.coderadar.job.JobLogger;
import org.wickedsource.coderadar.job.core.Job;
import org.wickedsource.coderadar.job.core.JobRepository;
import org.wickedsource.coderadar.job.core.ProcessingStatus;
import org.wickedsource.coderadar.job.core.ResultStatus;
import org.wickedsource.coderadar.job.queue.JobQueueService;
import org.wickedsource.coderadar.job.queue.JobUpdater;
import org.wickedsource.coderadar.testframework.template.IntegrationTestTemplate;

public class JobExecutionServiceTest extends IntegrationTestTemplate {

  @Autowired private JobRepository jobRepository;

  @Autowired private JobQueueService jobQueueService;

  @Autowired private JobUpdater jobUpdater;

  @Autowired private JobLogger jobLogger;

  @Mock private JobExecutor jobExecutor;

  @Test
  @DatabaseSetup(SINGLE_PROJECT_WITH_WAITING_JOB)
  public void testSuccessfulExecution() {
    Job jobBeforeExecution = jobRepository.findOne(1l);

    JobExecutionService executionService =
        new JobExecutionService(jobLogger, jobQueueService, jobUpdater, jobExecutor);
    executionService.executeNextJobInQueue();

    Job jobAfterExecution = jobRepository.findOne(jobBeforeExecution.getId());
    Assert.assertEquals(ResultStatus.SUCCESS, jobAfterExecution.getResultStatus());
    Assert.assertEquals(ProcessingStatus.PROCESSED, jobAfterExecution.getProcessingStatus());
  }

  @Test
  @DatabaseSetup(SINGLE_PROJECT_WITH_WAITING_JOB)
  public void testFailedExecution() {
    Job jobBeforeExecution = jobRepository.findOne(1l);

    doThrow(new RuntimeException("bwaaah")).when(jobExecutor).execute(any(Job.class));

    JobExecutionService executionService =
        new JobExecutionService(jobLogger, jobQueueService, jobUpdater, jobExecutor);
    executionService.executeNextJobInQueue();

    Job jobAfterExecution = jobRepository.findOne(jobBeforeExecution.getId());
    Assert.assertEquals(ResultStatus.FAILED, jobAfterExecution.getResultStatus());
    Assert.assertEquals(ProcessingStatus.PROCESSED, jobAfterExecution.getProcessingStatus());
  }
}
