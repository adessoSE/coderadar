package org.wickedsource.coderadar.job.analyze;

import org.springframework.data.repository.CrudRepository;
import org.wickedsource.coderadar.job.core.ProcessingStatus;

import java.util.List;

public interface AnalyzeCommitJobRepository extends CrudRepository<AnalyzeCommitJob, Long>{

    int deleteByProjectId(Long projectId);

    int countByProjectIdAndProcessingStatusIn(Long projectId, List<ProcessingStatus> statusList);

}
