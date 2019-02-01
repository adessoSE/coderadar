package org.wickedsource.coderadar.job.analyze;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.wickedsource.coderadar.job.core.ProcessingStatus;

public interface AnalyzeCommitJobRepository extends CrudRepository<AnalyzeCommitJob, Long> {

	int deleteByProjectId(Long projectId);

	int countByProjectIdAndProcessingStatusIn(Long projectId, List<ProcessingStatus> statusList);
}
