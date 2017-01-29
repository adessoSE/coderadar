package org.wickedsource.coderadar.job.associate;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.wickedsource.coderadar.job.core.ProcessingStatus;

public interface MergeLogJobRepository extends CrudRepository<AssociateGitLogJob, Long> {

  int countByProcessingStatusInAndProjectId(List<ProcessingStatus> status, Long projectId);
}
