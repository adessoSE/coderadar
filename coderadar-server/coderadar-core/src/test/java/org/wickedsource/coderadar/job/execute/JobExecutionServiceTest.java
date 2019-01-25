package org.wickedsource.coderadar.job.execute;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.Jobs.SINGLE_PROJECT_WITH_WAITING_JOB;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import java.util.Optional;
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
    Optional<Job> jobBeforeExecution = jobRepository.findById(1L);
    Assert.assertTrue(jobBeforeExecution.isPresent());

    JobExecutionService executionService =
        new JobExecutionService(jobLogger, jobQueueService, jobUpdater, jobExecutor);
    executionService.executeNextJobInQueue();

    Optional<Job> jobAfterExecution = jobRepository.findById(jobBeforeExecution.get().getId());
    Assert.assertTrue(jobAfterExecution.isPresent());
    Assert.assertEquals(ResultStatus.SUCCESS, jobAfterExecution.get().getResultStatus());
    Assert.assertEquals(ProcessingStatus.PROCESSED, jobAfterExecution.get().getProcessingStatus());
  }

  @Test
  @DatabaseSetup(SINGLE_PROJECT_WITH_WAITING_JOB)
  public void testFailedExecution() {
    Optional<Job> jobBeforeExecution = jobRepository.findById(1L);
    Assert.assertTrue(jobBeforeExecution.isPresent());

    doThrow(new RuntimeException("bwaaah")).when(jobExecutor).execute(any(Job.class));

    JobExecutionService executionService =
        new JobExecutionService(jobLogger, jobQueueService, jobUpdater, jobExecutor);
    executionService.executeNextJobInQueue();

    Optional<Job> jobAfterExecution = jobRepository.findById(jobBeforeExecution.get().getId());
    Assert.assertTrue(jobAfterExecution.isPresent());

    Assert.assertEquals(ResultStatus.FAILED, jobAfterExecution.get().getResultStatus());
    Assert.assertEquals(ProcessingStatus.PROCESSED, jobAfterExecution.get().getProcessingStatus());
  }
}
