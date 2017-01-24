package org.wickedsource.coderadar.job.queue;

import org.wickedsource.coderadar.job.core.Job;

public class JobDeletedException extends RuntimeException {

  public JobDeletedException(Job job) {
    super(String.format("Job was deleted while being processed: %s", job));
  }
}
