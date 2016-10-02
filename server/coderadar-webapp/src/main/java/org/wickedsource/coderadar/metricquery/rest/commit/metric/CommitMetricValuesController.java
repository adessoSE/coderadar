package org.wickedsource.coderadar.metricquery.rest.commit.metric;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters;
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
import org.wickedsource.coderadar.core.rest.dates.Day;
import org.wickedsource.coderadar.core.rest.dates.series.DayPoint;
import org.wickedsource.coderadar.core.rest.dates.series.Series;
import org.wickedsource.coderadar.core.rest.validation.ResourceNotFoundException;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValue;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueDTO;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository;
import org.wickedsource.coderadar.metricquery.rest.commit.DateSeries;
import org.wickedsource.coderadar.metricquery.rest.commit.SeriesFactory;
import org.wickedsource.coderadar.project.rest.ProjectVerifier;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
@ExposesResourceFor(MetricValue.class)
@Transactional
@RequestMapping(path = "/projects/{projectId}/metricvalues")
public class CommitMetricValuesController {

    private ProjectVerifier projectVerifier;

    private MetricValueRepository metricValueRepository;

    private CommitRepository commitRepository;

    private SeriesFactory seriesFactory;

    @Autowired
    public CommitMetricValuesController(ProjectVerifier projectVerifier, MetricValueRepository metricValueRepository, CommitRepository commitRepository, SeriesFactory seriesFactory) {
        this.projectVerifier = projectVerifier;
        this.metricValueRepository = metricValueRepository;
        this.commitRepository = commitRepository;
        this.seriesFactory = seriesFactory;
    }

    @RequestMapping(path = "/perCommit", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/hal+json")
    public ResponseEntity<CommitMetricsResource> queryMetrics(@PathVariable Long projectId, @Valid @RequestBody CommitMetricsQuery query) {
        projectVerifier.checkProjectExistsOrThrowException(projectId);

        Commit commit = commitRepository.findByName(query.getCommit());
        if (commit == null) {
            throw new ResourceNotFoundException();
        }

        CommitMetricsResource resource = new CommitMetricsResource();
        List<MetricValueDTO> commitMetricValues = metricValueRepository.findValuesAggregatedByCommitAndMetric(projectId, commit.getSequenceNumber(), query.getMetrics());
        resource.addMetricValues(commitMetricValues);
        resource.addAbsentMetrics(query.getMetrics());

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(path = "/history", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/hal+json")
    @SuppressWarnings("unchecked")
    public ResponseEntity<MetricValueHistoryResource> queryMetricsHistory(@PathVariable Long projectId, @Valid @RequestBody CommitMetricsHistoryQuery query) {
        projectVerifier.checkProjectExistsOrThrowException(projectId);

        MetricValueHistoryResource<Day> resource = new MetricValueHistoryResource<>();
        Series historySeries;
        switch (query.getInterval()) {
            case DAY:
                historySeries = createDaySeries(query.getMetric(), query.getDateRange().getStartDate(), query.getDateRange().getEndDate(), projectId);
                break;
            default:
                throw new IllegalStateException(String.format("unknown Interval type %s", query.getInterval()));

        }
        resource.setPoints(historySeries.getPoints());

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * Gets the values of a specified metric over the whole codebase for each day in a date range.
     *
     * @param metricName     the name of the metric in question.
     * @param startLocalDate the start date.
     * @param endLocalDate   the end date.
     * @param projectId      ID of the project whose metrics to query.
     * @return a series with the metric value as they have been at each day between startLocalDate and endLocalDate (both inclusive).
     */
    private DateSeries<Day> createDaySeries(String metricName, LocalDate startLocalDate, LocalDate endLocalDate, Long projectId) {

        DateSeries<Day> series = seriesFactory.daySeries(startLocalDate, endLocalDate);
        Date startDate = Jsr310Converters.LocalDateToDateConverter.INSTANCE.convert(startLocalDate);
        Date endDate = Jsr310Converters.LocalDateToDateConverter.INSTANCE.convert(endLocalDate);

        List<Commit> lastCommitsPerDay = commitRepository.findLastForEachDay(projectId, startDate, endDate);
        for (Commit commit : lastCommitsPerDay) {
            List<MetricValueDTO> values = metricValueRepository.findValuesAggregatedByCommitAndMetric(projectId, commit.getSequenceNumber(), Collections.singletonList(metricName));
            if (values.size() > 1) {
                throw new IllegalStateException("result should only contain a single metric value!");
            }
            if (values.size() == 1) {
                MetricValueDTO value = values.get(0);
                DayPoint point = DayPoint.from(commit.getTimestamp(), value.getValue());
                series.addPoint(point);
            }
        }
        series.fillMissingValuesWithPreviousValue();
        return series;
    }

}
