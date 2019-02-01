package org.wickedsource.coderadar.job.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.wickedsource.coderadar.job.core.Job;
import org.wickedsource.coderadar.job.core.JobRepository;
import org.wickedsource.coderadar.job.core.ProcessingStatus;

@Service
public class JobDequeuer {

	@Autowired private JobRepository jobRepository;

	/**
	* Takes the Job waiting longest from the job queue and marks it as being processed.
	*
	* <p>Technical Note: this method must run in it's own transaction (transaction propagation
	* REQUIRES_NEW). This is to minimize the time between loading the next job from the queue and
	* marking it as being processed. Multiple concurrent calls to the dequeue method may result in
	* optimistic locking exceptions which should be handled by the client, for example by just trying
	* again.
	*
	* @return the next Job in line or null if queue is empty or the next UpdateJob could not be
	*     loaded due to conflict with other queue clients.
	*/
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Job dequeue() {
		Job job = jobRepository.findTop1ByProcessingStatusOrderByQueuedDate(ProcessingStatus.WAITING);
		if (job == null) {
			return null;
		}
		job.setProcessingStatus(ProcessingStatus.PROCESSING);
		job = jobRepository.save(job);
		return job;
	}
}
