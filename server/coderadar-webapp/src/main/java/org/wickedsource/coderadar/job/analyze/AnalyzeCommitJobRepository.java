package org.wickedsource.coderadar.job.analyze;

import org.springframework.data.repository.CrudRepository;
import org.wickedsource.coderadar.job.core.ProcessingStatus;

public interface AnalyzeCommitJobRepository extends CrudRepository<AnalyzeCommitJob, Long>{

    int countByCommitIdAndProcessingStatus(Long commitId, ProcessingStatus status);

}
