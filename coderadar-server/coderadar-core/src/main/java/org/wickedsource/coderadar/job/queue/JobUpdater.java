package org.wickedsource.coderadar.job.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.wickedsource.coderadar.job.core.Job;
import org.wickedsource.coderadar.job.core.JobRepository;
import org.wickedsource.coderadar.project.domain.ProjectRepository;
import org.wickedsource.coderadar.projectadministration.domain.Project;

@Service
public class JobUpdater {

  private JobRepository updateJobRepository;

  private ProjectRepository projectRepository;

  private JobRepository jobRepository;

  @Autowired
  public JobUpdater(
      JobRepository updateJobRepository,
      ProjectRepository projectRepository,
      JobRepository jobRepository) {
    this.updateJobRepository = updateJobRepository;
    this.projectRepository = projectRepository;
    this.jobRepository = jobRepository;
  }

  /**
   * Embeds updating a Job in a separate transaction so it can be updated in the database even if
   * the outer transaction is marked for rollback.
   *
   * @param job the job to update in the database.
   */
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void updateJob(Job job) {
    if (!jobExists(job)) {
      throw new JobDeletedException(job);
    }

    if (projectExists(job.getProject())) {
      updateJobRepository.save(job);
    }
  }

  private boolean projectExists(Project project) {
    return projectRepository.countById(project.getId()) > 0;
  }

  private boolean jobExists(Job job) {
    return jobRepository.countById(job.getId()) > 0;
  }
}
