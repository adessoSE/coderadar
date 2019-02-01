package org.wickedsource.coderadar.metricquery.rest.commit.metric.series;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.core.rest.dates.Day;
import org.wickedsource.coderadar.core.rest.dates.series.DayPoint;
import org.wickedsource.coderadar.core.rest.dates.series.Point;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository;
import org.wickedsource.coderadar.metricquery.rest.commit.DateSeries;

@Component
public class DaySeriesFactory extends MetricValueSeriesFactory<Day> {

	private CommitRepository commitRepository;

	@Autowired
	public DaySeriesFactory(
			MetricValueRepository metricValueRepository, CommitRepository commitRepository) {
		super(metricValueRepository);
		this.commitRepository = commitRepository;
	}

	@Override
	protected DateSeries<Day> createEmptySeries(LocalDate startDate, LocalDate endDate) {
		DateSeries<Day> series = new DateSeries<>();
		for (int i = 0; i < DAYS.between(startDate, endDate) + 1; i++) {
			LocalDate nextDate = startDate.plus(i, DAYS);
			series.addPoint(
					new DayPoint(
							new Day(nextDate.getYear(), nextDate.getMonthValue(), nextDate.getDayOfMonth()),
							null));
		}
		return series;
	}

	@Override
	protected List<Commit> getLastCommitPerInterval(
			Long projectId, LocalDate startDate, LocalDate endDate) {
		return commitRepository.findLastForEachDay(
				projectId,
				Jsr310Converters.LocalDateToDateConverter.INSTANCE.convert(startDate),
				Jsr310Converters.LocalDateToDateConverter.INSTANCE.convert(endDate));
	}

	@Override
	protected Point<Day, Long> createPointForCommit(Commit commit, Long value) {
		Day day =
				new Day(
						commit.getDateCoordinates().getYear(),
						commit.getDateCoordinates().getMonth(),
						commit.getDateCoordinates().getDayOfMonth());
		return new DayPoint(day, value);
	}
}
