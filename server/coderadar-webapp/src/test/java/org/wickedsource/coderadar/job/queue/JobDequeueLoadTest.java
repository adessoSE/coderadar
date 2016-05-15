package org.wickedsource.coderadar.job.queue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.wickedsource.coderadar.IntegrationTestTemplate;
import org.wickedsource.coderadar.factories.Factories;
import org.wickedsource.coderadar.job.domain.Job;
import org.wickedsource.coderadar.job.domain.JobRepository;
import org.wickedsource.coderadar.job.domain.ScanVcsJob;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class JobDequeueLoadTest extends IntegrationTestTemplate {

    private Logger logger = LoggerFactory.getLogger(JobDequeueLoadTest.class);

    @Autowired
    private JobQueueService service;

    @Autowired
    private JobRepository repository;

    @Test
    public void loadTestDequeueJob() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            ScanVcsJob job = Factories.job().waitingPullJob();
            job.setId(null);
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