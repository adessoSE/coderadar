package org.wickedsource.coderadar.job.execute;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.wickedsource.coderadar.IntegrationTestTemplate;
import org.wickedsource.coderadar.factories.Factories;
import org.wickedsource.coderadar.job.core.Job;
import org.wickedsource.coderadar.job.core.JobRepository;
import org.wickedsource.coderadar.job.core.ProcessingStatus;
import org.wickedsource.coderadar.job.core.ResultStatus;
import org.wickedsource.coderadar.job.queue.JobQueueService;
import org.wickedsource.coderadar.job.queue.JobUpdater;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;

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
    private JobExecutor jobExecutor;

    @Autowired
    private ProjectRepository projectRepository;


    @Test
    public void testSuccessfulExecution() {
        Job jobBeforeExecution = initJob();

        JobExecutionService executionService = new JobExecutionService(jobQueueService, jobUpdater, jobExecutor);
        executionService.executeNextJobInQueue();

        Job jobAfterExecution = jobRepository.findOne(jobBeforeExecution.getId());
        Assert.assertEquals(ResultStatus.SUCCESS, jobAfterExecution.getResultStatus());
        Assert.assertEquals(ProcessingStatus.PROCESSED, jobAfterExecution.getProcessingStatus());
    }

    private Job initJob() {
        Project project = projectRepository.save(Factories.project().validProject());
        Job jobBeforeExecution = Factories.job().waitingPullJob();
        jobBeforeExecution.setId(null);
        jobBeforeExecution.setProject(project);
        jobBeforeExecution = jobRepository.save(jobBeforeExecution);
        Assert.assertEquals(null, jobBeforeExecution.getResultStatus());
        Assert.assertEquals(ProcessingStatus.WAITING, jobBeforeExecution.getProcessingStatus());
        return jobBeforeExecution;
    }

    @Test
    public void testFailedExecution() {
        Job jobBeforeExecution = initJob();

        doThrow(new RuntimeException("bwaaah")).when(jobExecutor).execute(any(Job.class));

        JobExecutionService executionService = new JobExecutionService(jobQueueService, jobUpdater, jobExecutor);
        executionService.executeNextJobInQueue();

        Job jobAfterExecution = jobRepository.findOne(jobBeforeExecution.getId());
        Assert.assertEquals(ResultStatus.FAILED, jobAfterExecution.getResultStatus());
        Assert.assertEquals(ProcessingStatus.PROCESSED, jobAfterExecution.getProcessingStatus());
    }


}