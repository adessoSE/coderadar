package org.wickedsource.coderadar.job.domain;

import org.springframework.data.repository.CrudRepository;

public interface AnalyzeCommitJobRepository extends CrudRepository<AnalyzeCommitJob, Long>{

    int countByCommitIdAndProcessingStatus(Long commitId, ProcessingStatus status);

}
