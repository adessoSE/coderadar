package org.wickedsource.coderadar.job.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.job.core.Job;
import org.wickedsource.coderadar.job.core.JobRepository;
import org.wickedsource.coderadar.job.core.ProcessingStatus;

@Service
public class JobQueueService {

	private JobDequeuer dequeuer;

	private JobRepository jobRepository;

	private static final int maxDequeueAttempts = 5;

	@Autowired
	public JobQueueService(JobDequeuer dequeuer, JobRepository jobRepository) {
		this.dequeuer = dequeuer;
		this.jobRepository = jobRepository;
	}

	/**
	* Loads the next Job from the job queue. If another client tries to dequeue an item at the same
	* time, this method will just try again up to a maximum of 5 attempts. If no item can be dequeued
	* after 5 attempts, this method returns NULL.
	*
	* @return the next Job in queue or NULL, if the queue is empty or after 5 unsuccessful tries due
	*     to conflict with other transactions trying to dequeue as well.
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

	/**
	* Returns true if the job queue is currently empty (i.e. there are no jobs waiting for
	* execution).
	*/
	public boolean isQueueEmpty() {
		return jobRepository.countByProcessingStatus(ProcessingStatus.WAITING) == 0;
	}
}
