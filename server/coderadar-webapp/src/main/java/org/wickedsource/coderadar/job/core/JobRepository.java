package org.wickedsource.coderadar.job.core;

import org.springframework.data.repository.CrudRepository;

public interface JobRepository extends CrudRepository<Job, Long> {

    /**
     * Selects the oldest Job with the specified ProcessingStatus from the queue.
     */
    Job findTop1ByProcessingStatusOrderByQueuedDate(ProcessingStatus status);

    /**
     * Returns the number of jobs in the database with the given processing status.
     */
    int countByProcessingStatus(ProcessingStatus status);

}
