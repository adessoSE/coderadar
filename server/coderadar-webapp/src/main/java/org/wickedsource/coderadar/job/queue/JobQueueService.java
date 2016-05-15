package org.wickedsource.coderadar.job.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.job.domain.Job;

@Service
public class JobQueueService {

    private JobDequeuer dequeuer;

    private static final int maxDequeueAttempts = 5;

    @Autowired
    public JobQueueService(JobDequeuer dequeuer) {
        this.dequeuer = dequeuer;
    }

    /**
     * Loads the next Job from the job queue. If another client tries to dequeue an item at the same time,
     * this method will just try again up to a maximum of 5 attempts. If no item can be dequeued after 5 attempts, this method
     * returns NULL.
     *
     * @return the next Job in queue or NULL, if the queue is empty or after 5 unsuccessful tries due to conflict
     * with other transactions trying to dequeue as well.
     */
    public Job dequeue() {
        int attemptCount = 1;
        while (attemptCount <= maxDequeueAttempts) {
            try {
                return dequeuer.dequeue();
            } catch (ObjectOptimisticLockingFailureException e) {
                // Another transaction has already dequeued the job we just wanted to dequeue ourselves.
                // Thus, we try again.
                attemptCount++;
            }
        }
        return null;
    }

}
