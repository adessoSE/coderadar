package org.wickedsource.coderadar.metricquery.rest.commit.metric;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.core.common.ResourceNotFoundException;
import org.wickedsource.coderadar.core.rest.dates.Day;
import org.wickedsource.coderadar.core.rest.dates.series.Series;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValue;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueDTO;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository;
import org.wickedsource.coderadar.metricquery.rest.commit.metric.series.DaySeriesFactory;
import org.wickedsource.coderadar.metricquery.rest.commit.metric.series.WeekSeriesFactory;
import org.wickedsource.coderadar.project.rest.ProjectVerifier;

@Controller
@ExposesResourceFor(MetricValue.class)
@Transactional
@RequestMapping(path = "/projects/{projectId}/metricvalues")
public class CommitMetricValuesController {

  private ProjectVerifier projectVerifier;

  private MetricValueRepository metricValueRepository;

  private CommitRepository commitRepository;

  private DaySeriesFactory daySeriesFactory;

  private WeekSeriesFactory weekSeriesFactory;

  @Autowired
  public CommitMetricValuesController(
      ProjectVerifier projectVerifier,
      MetricValueRepository metricValueRepository,
      CommitRepository commitRepository,
      DaySeriesFactory daySeriesFactory,
      WeekSeriesFactory weekSeriesFactory) {
    this.projectVerifier = projectVerifier;
    this.metricValueRepository = metricValueRepository;
    this.commitRepository = commitRepository;
    this.daySeriesFactory = daySeriesFactory;
    this.weekSeriesFactory = weekSeriesFactory;
  }

  @RequestMapping(
    path = "/perCommit",
    method = {RequestMethod.GET, RequestMethod.POST},
    produces = "application/hal+json"
  )
  public ResponseEntity<CommitMetricsResource> queryMetrics(
      @PathVariable Long projectId, @Valid @RequestBody CommitMetricsQuery query) {
    projectVerifier.checkProjectExistsOrThrowException(projectId);

    Commit commit = commitRepository.findByName(query.getCommit());
    if (commit == null) {
      throw new ResourceNotFoundException();
    }

    CommitMetricsResource resource = new CommitMetricsResource();
    List<MetricValueDTO> commitMetricValues =
        metricValueRepository.findValuesAggregatedByCommitAndMetric(
            projectId, commit.getSequenceNumber(), query.getMetrics());
    resource.addMetricValues(commitMetricValues);
    resource.addAbsentMetrics(query.getMetrics());

    return new ResponseEntity<>(resource, HttpStatus.OK);
  }

  @RequestMapping(
    path = "/history",
    method = {RequestMethod.GET, RequestMethod.POST},
    produces = "application/hal+json"
  )
  @SuppressWarnings("unchecked")
  public ResponseEntity<MetricValueHistoryResource> queryMetricsHistory(
      @PathVariable Long projectId, @Valid @RequestBody CommitMetricsHistoryQuery query) {
    projectVerifier.checkProjectExistsOrThrowException(projectId);

    MetricValueHistoryResource<Day> resource = new MetricValueHistoryResource<>();
    Series historySeries;
    switch (query.getInterval()) {
      case DAY:
        historySeries =
            daySeriesFactory.createSeries(
                projectId,
                query.getMetric(),
                query.getDateRange().getStartDate(),
                query.getDateRange().getEndDate());
        break;
      case WEEK:
        historySeries =
            weekSeriesFactory.createSeries(
                projectId,
                query.getMetric(),
                query.getDateRange().getStartDate(),
                query.getDateRange().getEndDate());
        break;
      default:
        throw new IllegalStateException(
            String.format("unknown Interval type %s", query.getInterval()));
    }
    resource.setPoints(historySeries.getPoints());

    return new ResponseEntity<>(resource, HttpStatus.OK);
  }
}
