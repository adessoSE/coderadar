package org.wickedsource.coderadar.job.domain;

import org.springframework.data.repository.CrudRepository;

public interface JobRepository extends CrudRepository<Job, Long> {

    /**
     * Selects the oldest Job with the specified ProcessingStatus from the queue.
     */
    Job findTop1ByProcessingStatusOrderByQueuedDate(ProcessingStatus status);

}
