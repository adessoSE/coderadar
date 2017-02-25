package org.wickedsource.coderadar.job.core;

import static org.wickedsource.coderadar.factories.entities.EntityFactory.job;
import static org.wickedsource.coderadar.factories.entities.EntityFactory.project;

import java.util.Date;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.wickedsource.coderadar.job.scan.commit.ScanCommitsJob;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;
import org.wickedsource.coderadar.testframework.template.IntegrationTestTemplate;

public class JobRepositoryTest extends IntegrationTestTemplate {

  @Autowired private JobRepository repository;

  @Autowired private ProjectRepository projectRepository;

  @Test
  @DirtiesContext
  public void findTop1() {

    Project project = projectRepository.save(project().validProject());

    ScanCommitsJob job1 = job().waitingPullJob();
    job1.setId(null);
    job1.setQueuedDate(new Date(System.currentTimeMillis() - 600));
    job1.setProject(project);
    job1 = repository.save(job1);

    ScanCommitsJob job2 = job().waitingPullJob();
    job2.setId(null);
    job2.setProject(project);
    repository.save(job2);

    Job foundJob = repository.findTop1ByProcessingStatusOrderByQueuedDate(ProcessingStatus.WAITING);

    Assert.assertEquals(job1.getId(), foundJob.getId());
  }
}
