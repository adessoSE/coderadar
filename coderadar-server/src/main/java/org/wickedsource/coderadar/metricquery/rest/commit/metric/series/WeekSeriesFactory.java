package org.wickedsource.coderadar.metricquery.rest.commit.metric.series;

import static java.time.temporal.ChronoUnit.WEEKS;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.commit.domain.DateCoordinates;
import org.wickedsource.coderadar.core.configuration.CoderadarConfiguration;
import org.wickedsource.coderadar.core.rest.dates.Week;
import org.wickedsource.coderadar.core.rest.dates.series.Point;
import org.wickedsource.coderadar.core.rest.dates.series.WeekPoint;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository;
import org.wickedsource.coderadar.metricquery.rest.commit.DateSeries;

@Component
public class WeekSeriesFactory extends MetricValueSeriesFactory<Week> {

  private CommitRepository commitRepository;

  private CoderadarConfiguration config;

  @Autowired
  public WeekSeriesFactory(
      MetricValueRepository metricValueRepository,
      CommitRepository commitRepository,
      CoderadarConfiguration config) {
    super(metricValueRepository);
    this.commitRepository = commitRepository;
    this.config = config;
  }

  @Override
  protected DateSeries<Week> createEmptySeries(LocalDate startDate, LocalDate endDate) {
    DateSeries<Week> series = new DateSeries<>();
    for (int i = 0; i < WEEKS.between(startDate, endDate) + 1; i++) {
      LocalDate nextDate = startDate.plus(i, WEEKS);
      DateCoordinates dateCoordinates =
          new DateCoordinates(
              Jsr310Converters.LocalDateToDateConverter.INSTANCE.convert(nextDate),
              config.getDateLocale());
      Week week = new Week(dateCoordinates.getYearOfWeek(), dateCoordinates.getWeekOfYear());
      series.addPoint(new WeekPoint(week, null));
    }
    return series;
  }

  @Override
  protected List<Commit> getLastCommitPerInterval(
      Long projectId, LocalDate startDate, LocalDate endDate) {
    return commitRepository.findLastForEachWeek(
        projectId,
        Jsr310Converters.LocalDateToDateConverter.INSTANCE.convert(startDate),
        Jsr310Converters.LocalDateToDateConverter.INSTANCE.convert(endDate));
  }

  @Override
  protected Point<Week, Long> createPointForCommit(Commit commit, Long value) {
    Week week =
        new Week(
            commit.getDateCoordinates().getYearOfWeek(),
            commit.getDateCoordinates().getWeekOfYear());
    return new WeekPoint(week, value);
  }
}
