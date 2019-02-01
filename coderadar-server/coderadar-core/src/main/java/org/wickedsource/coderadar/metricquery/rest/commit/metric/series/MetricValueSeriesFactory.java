package org.wickedsource.coderadar.metricquery.rest.commit.metric.series;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.core.rest.dates.series.Point;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueDTO;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository;
import org.wickedsource.coderadar.metricquery.rest.commit.DateSeries;

public abstract class MetricValueSeriesFactory<D extends Comparable> {

	private MetricValueRepository metricValueRepository;

	public MetricValueSeriesFactory(MetricValueRepository metricValueRepository) {
		this.metricValueRepository = metricValueRepository;
	}

	protected abstract DateSeries<D> createEmptySeries(LocalDate startDate, LocalDate endDate);

	protected abstract List<Commit> getLastCommitPerInterval(
			Long projectId, LocalDate startDate, LocalDate endDate);

	protected abstract Point<D, Long> createPointForCommit(Commit commit, Long value);

	public DateSeries<D> createSeries(
			Long projectId, String metricName, LocalDate startLocalDate, LocalDate endLocalDate) {
		DateSeries<D> series = createEmptySeries(startLocalDate, endLocalDate);
		List<Commit> lastCommitsPerDay =
				getLastCommitPerInterval(projectId, startLocalDate, endLocalDate);
		for (Commit commit : lastCommitsPerDay) {
			List<MetricValueDTO> values =
					metricValueRepository.findValuesAggregatedByCommitAndMetric(
							projectId, commit.getSequenceNumber(), Collections.singletonList(metricName));
			if (values.size() > 1) {
				throw new IllegalStateException("result should only contain a single metric value!");
			}
			if (values.size() == 1) {
				MetricValueDTO value = values.get(0);
				Point<D, Long> point = createPointForCommit(commit, value.getValue());
				series.addPoint(point);
			}
		}
		series.fillMissingValuesWithPreviousValue();
		return series;
	}
}
