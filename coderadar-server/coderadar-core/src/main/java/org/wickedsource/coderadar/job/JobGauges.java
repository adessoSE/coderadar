package org.wickedsource.coderadar.job;

import static com.codahale.metrics.MetricRegistry.name;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.job.core.JobRepository;
import org.wickedsource.coderadar.job.core.ProcessingStatus;

@Component
public class JobGauges {

	@Autowired
	public JobGauges(MetricRegistry metricRegistry, JobRepository jobRepository) {

		metricRegistry.register(
				name(JobLogger.class, "inProgress"),
				new Gauge() {
					@Override
					public Object getValue() {
						return jobRepository.countByProcessingStatus(ProcessingStatus.PROCESSING);
					}
				});

		metricRegistry.register(
				name(JobLogger.class, "waiting"),
				new Gauge() {
					@Override
					public Object getValue() {
						return jobRepository.countByProcessingStatus(ProcessingStatus.WAITING);
					}
				});
	}
}
