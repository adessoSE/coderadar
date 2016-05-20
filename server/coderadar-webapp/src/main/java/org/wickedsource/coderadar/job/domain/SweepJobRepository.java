package org.wickedsource.coderadar.job.domain;

import org.springframework.data.repository.CrudRepository;

public interface SweepJobRepository extends CrudRepository<SweepJob, Long>{

    int countByCommitIdAndProcessingStatus(Long commitId, ProcessingStatus status);

}
