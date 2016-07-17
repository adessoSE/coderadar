package org.wickedsource.coderadar.job.queue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.wickedsource.coderadar.job.core.Job;
import org.wickedsource.coderadar.job.core.JobRepository;
import org.wickedsource.coderadar.job.scan.commit.ScanCommitsJob;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;
import org.wickedsource.coderadar.testframework.template.IntegrationTestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.wickedsource.coderadar.factories.entities.EntityFactory.job;
import static org.wickedsource.coderadar.factories.entities.EntityFactory.project;

public class JobDequeueLoadTest extends IntegrationTestTemplate {

    private Logger logger = LoggerFactory.getLogger(JobDequeueLoadTest.class);

    @Autowired
    private JobQueueService service;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private JobRepository repository;

    @Test
    public void loadTestDequeueJob() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            Project project = projectRepository.save(project().validProject());
            ScanCommitsJob job = job().waitingPullJob();
            job.setId(null);
            job.setProject(project);
            repository.save(job);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executorService.execute(() -> {
                Job job = service.dequeue();
                if(job != null) {
                    logger.info("dequeued job {}", job.getId());
                }else{
                    logger.info("dequeue attempt FAILED");
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
    }

}