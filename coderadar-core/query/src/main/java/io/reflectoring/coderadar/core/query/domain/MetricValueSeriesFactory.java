package io.reflectoring.coderadar.core.query.domain;

import io.reflectoring.coderadar.core.analyzer.domain.Commit;
import io.reflectoring.coderadar.core.analyzer.domain.MetricValueDTO;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

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
