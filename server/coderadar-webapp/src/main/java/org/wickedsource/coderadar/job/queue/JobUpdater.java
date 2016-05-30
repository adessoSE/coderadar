package org.wickedsource.coderadar.job.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.wickedsource.coderadar.job.core.Job;
import org.wickedsource.coderadar.job.core.JobRepository;

@Service
public class JobUpdater {

    @Autowired
    private JobRepository updateJobRepository;

    /**
     * Embeds updating a Job in a separate transaction so it can be updated in the database even if the outer
     * transaction is marked for rollback.
     *
     * @param job the job to update in the database.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Job updateJob(Job job) {
        return updateJobRepository.save(job);
    }

}
