package org.wickedsource.coderadar.job.queue;

import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.Projects.SINGLE_PROJECT;
import static org.wickedsource.coderadar.factories.entities.EntityFactory.job;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.wickedsource.coderadar.job.core.Job;
import org.wickedsource.coderadar.job.core.JobRepository;
import org.wickedsource.coderadar.job.scan.commit.ScanCommitsJob;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;
import org.wickedsource.coderadar.testframework.template.IntegrationTestTemplate;

public class JobDequeueLoadTest extends IntegrationTestTemplate {

  private Logger logger = LoggerFactory.getLogger(JobDequeueLoadTest.class);

  @Autowired private JobQueueService service;

  @Autowired private ProjectRepository projectRepository;

  @Autowired private JobRepository repository;

  @Test
  @DatabaseSetup(SINGLE_PROJECT)
  public void loadTestDequeueJob() throws InterruptedException {
    for (int i = 0; i < 100; i++) {
      Optional<Project> project = projectRepository.findById(1L);
      ScanCommitsJob job = job().waitingPullJob();
      job.setId(null);
      job.setProject(project.get());
      repository.save(job);
    }

    ExecutorService executorService = Executors.newFixedThreadPool(10);
    for (int i = 0; i < 100; i++) {
      executorService.execute(
          () -> {
            Job job = service.dequeue();
            if (job != null) {
              logger.info("dequeued job {}", job.getId());
            } else {
              logger.info("dequeue attempt FAILED");
            }
          });
    }
    executorService.shutdown();
    executorService.awaitTermination(1, TimeUnit.MINUTES);
  }
}
