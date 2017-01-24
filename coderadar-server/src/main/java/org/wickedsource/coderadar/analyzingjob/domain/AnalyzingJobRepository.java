package org.wickedsource.coderadar.analyzingjob.domain;

import org.springframework.data.repository.CrudRepository;

public interface AnalyzingJobRepository extends CrudRepository<AnalyzingJob, Long> {

  AnalyzingJob findByProjectId(long projectId);

  int deleteByProjectId(long projectId);
}
