package org.wickedsource.coderadar.job.domain;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.wickedsource.coderadar.IntegrationTestTemplate;
import org.wickedsource.coderadar.factories.Factories;

import java.util.Date;

public class JobRepositoryTest extends IntegrationTestTemplate {

    @Autowired
    private JobRepository repository;

    @Test
    @DirtiesContext
    public void findTop1() {
        PullJob job1 = Factories.job().waitingPullJob();
        job1.setId(null);
        job1.setQueuedDate(new Date(System.currentTimeMillis() - 600));
        job1 = repository.save(job1);

        PullJob job2 = Factories.job().waitingPullJob();
        job2.setId(null);
        repository.save(job2);

        Job foundJob = repository.findTop1ByProcessingStatusOrderByQueuedDate(ProcessingStatus.WAITING);

        Assert.assertEquals(job1.getId(), foundJob.getId());
    }

}