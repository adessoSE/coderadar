package org.wickedsource.coderadar.metricquery.rest.commit.metric;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.MetricValues.SINGLE_PROJECT_WITH_METRICS;
import static org.wickedsource.coderadar.testframework.template.JsonHelper.fromJson;
import static org.wickedsource.coderadar.testframework.template.JsonHelper.toJsonWithoutLinks;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.containsResource;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.status;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.wickedsource.coderadar.core.rest.dates.Day;
import org.wickedsource.coderadar.core.rest.dates.Week;
import org.wickedsource.coderadar.core.rest.dates.series.DayPoint;
import org.wickedsource.coderadar.core.rest.dates.series.WeekPoint;
import org.wickedsource.coderadar.metricquery.rest.commit.DateRange;
import org.wickedsource.coderadar.metricquery.rest.commit.Interval;
import org.wickedsource.coderadar.testframework.template.ControllerTestTemplate;

public class CommitMetricValuesControllerTest extends ControllerTestTemplate {

  @Test
  @DatabaseSetup(SINGLE_PROJECT_WITH_METRICS)
  @ExpectedDatabase(SINGLE_PROJECT_WITH_METRICS)
  public void queryCommitMetrics() throws Exception {

    CommitMetricsQuery query = new CommitMetricsQuery();
    query.setCommit("eb7cbd429530dc26d06a9ea2a62794421dce1e9a");
    query.addMetrics("metric1", "metric2", "metric3", "metric4");

    ConstrainedFields requestFields = fields(CommitMetricsQuery.class);

    MvcResult result =
        mvc()
            .perform(
                get("/projects/1/metricvalues/perCommit")
                    .content(toJsonWithoutLinks(query))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(containsResource(CommitMetricsResource.class))
            .andDo(
                document(
                    "metrics/commit/metrics",
                    requestFields(
                        requestFields
                            .withPath("commit")
                            .description("Name of the commit whose metric values to get."),
                        requestFields
                            .withPath("metrics")
                            .description(
                                "List of the names of the metrics whose values you want to query."))))
            .andReturn();

    CommitMetricsResource commitMetricsResource =
        fromJson(result.getResponse().getContentAsString(), CommitMetricsResource.class);
    assertThat(commitMetricsResource.getMetrics()).hasSize(4);
    assertThat(commitMetricsResource.getMetrics().get("metric1")).isEqualTo(26);
    assertThat(commitMetricsResource.getMetrics().get("metric2")).isEqualTo(28);
    assertThat(commitMetricsResource.getMetrics().get("metric3")).isEqualTo(14);
    assertThat(commitMetricsResource.getMetrics().get("metric4")).isEqualTo(16);
  }

  @Test
  @DatabaseSetup(SINGLE_PROJECT_WITH_METRICS)
  @ExpectedDatabase(SINGLE_PROJECT_WITH_METRICS)
  @SuppressWarnings("unchecked")
  public void historyByDay() throws Exception {

    CommitMetricsHistoryQuery query = new CommitMetricsHistoryQuery();

    query.setDateRange(new DateRange(LocalDate.of(2016, 2, 1), LocalDate.of(2016, 3, 1)));
    query.setMetric("metric1");
    query.setInterval(Interval.DAY);

    MvcResult result =
        mvc()
            .perform(
                get("/projects/1/metricvalues/history")
                    .content(toJsonWithoutLinks(query))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(containsResource(MetricValueHistoryResource.class))
            .andReturn();

    MetricValueHistoryResource historyResource =
        fromJson(result.getResponse().getContentAsString(), MetricValueHistoryResource.class);
    assertThat(historyResource.getPoints()).hasSize(30);
    assertThat(historyResource.getPoints()).contains(new DayPoint(new Day(2016, 2, 1), 10L));
    assertThat(historyResource.getPoints()).contains(new DayPoint(new Day(2016, 2, 2), 10L));
    assertThat(historyResource.getPoints()).contains(new DayPoint(new Day(2016, 2, 3), 10L));
    assertThat(historyResource.getPoints()).contains(new DayPoint(new Day(2016, 2, 4), 10L));
    assertThat(historyResource.getPoints()).contains(new DayPoint(new Day(2016, 2, 5), 10L));
    assertThat(historyResource.getPoints()).contains(new DayPoint(new Day(2016, 2, 6), 10L));
    assertThat(historyResource.getPoints()).contains(new DayPoint(new Day(2016, 2, 7), 10L));
    assertThat(historyResource.getPoints()).contains(new DayPoint(new Day(2016, 2, 8), 10L));
    assertThat(historyResource.getPoints()).contains(new DayPoint(new Day(2016, 2, 9), 10L));
    assertThat(historyResource.getPoints()).contains(new DayPoint(new Day(2016, 2, 10), 10L));
    assertThat(historyResource.getPoints()).contains(new DayPoint(new Day(2016, 2, 11), 10L));
    assertThat(historyResource.getPoints()).contains(new DayPoint(new Day(2016, 2, 12), 10L));
    assertThat(historyResource.getPoints()).contains(new DayPoint(new Day(2016, 2, 13), 10L));
    assertThat(historyResource.getPoints()).contains(new DayPoint(new Day(2016, 2, 14), 10L));
    assertThat(historyResource.getPoints()).contains(new DayPoint(new Day(2016, 2, 15), 10L));
    assertThat(historyResource.getPoints()).contains(new DayPoint(new Day(2016, 2, 16), 10L));
    assertThat(historyResource.getPoints()).contains(new DayPoint(new Day(2016, 2, 17), 10L));
    assertThat(historyResource.getPoints()).contains(new DayPoint(new Day(2016, 2, 18), 10L));
    assertThat(historyResource.getPoints()).contains(new DayPoint(new Day(2016, 2, 19), 10L));
    assertThat(historyResource.getPoints()).contains(new DayPoint(new Day(2016, 2, 20), 10L));
    assertThat(historyResource.getPoints()).contains(new DayPoint(new Day(2016, 2, 21), 10L));
    assertThat(historyResource.getPoints()).contains(new DayPoint(new Day(2016, 2, 22), 10L));
    assertThat(historyResource.getPoints()).contains(new DayPoint(new Day(2016, 2, 23), 10L));
    assertThat(historyResource.getPoints()).contains(new DayPoint(new Day(2016, 2, 24), 10L));
    assertThat(historyResource.getPoints()).contains(new DayPoint(new Day(2016, 2, 25), 10L));
    assertThat(historyResource.getPoints()).contains(new DayPoint(new Day(2016, 2, 26), 10L));
    assertThat(historyResource.getPoints()).contains(new DayPoint(new Day(2016, 2, 27), 10L));
    assertThat(historyResource.getPoints()).contains(new DayPoint(new Day(2016, 2, 28), 10L));
    assertThat(historyResource.getPoints()).contains(new DayPoint(new Day(2016, 2, 29), 10L));
    assertThat(historyResource.getPoints()).contains(new DayPoint(new Day(2016, 3, 1), 26L));
  }

  @Test
  @DatabaseSetup(SINGLE_PROJECT_WITH_METRICS)
  @ExpectedDatabase(SINGLE_PROJECT_WITH_METRICS)
  @SuppressWarnings("unchecked")
  public void historyByWeek() throws Exception {

    CommitMetricsHistoryQuery query = new CommitMetricsHistoryQuery();

    query.setDateRange(new DateRange(LocalDate.of(2016, 2, 1), LocalDate.of(2016, 4, 1)));
    query.setMetric("metric1");
    query.setInterval(Interval.WEEK);

    ConstrainedFields requestFields = fields(CommitMetricsHistoryQuery.class);

    MvcResult result =
        mvc()
            .perform(
                get("/projects/1/metricvalues/history")
                    .content(toJsonWithoutLinks(query))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(containsResource(MetricValueHistoryResource.class))
            .andDo(
                document(
                    "metrics/history/metrics",
                    requestFields(
                        requestFields
                            .withPath("dateRange.startDate")
                            .description("Date from which to start the metric history."),
                        requestFields
                            .withPath("dateRange.endDate")
                            .description("Date at which to end the metric history."),
                        requestFields
                            .withPath("metric")
                            .description("Name of the metric whose history to query."),
                        requestFields
                            .withPath("interval")
                            .description(
                                "The resolution of the history. The history result will contain one data point in each interval. Can be one of DAY, WEEK, MONTH or YEAR."))))
            .andReturn();

    MetricValueHistoryResource historyResource =
        fromJson(result.getResponse().getContentAsString(), MetricValueHistoryResource.class);
    assertThat(historyResource.getPoints()).hasSize(9);
    assertThat(historyResource.getPoints()).contains(new WeekPoint(new Week(2016, 5), 10L));
    assertThat(historyResource.getPoints()).contains(new WeekPoint(new Week(2016, 6), 10L));
    assertThat(historyResource.getPoints()).contains(new WeekPoint(new Week(2016, 7), 10L));
    assertThat(historyResource.getPoints()).contains(new WeekPoint(new Week(2016, 8), 10L));
    assertThat(historyResource.getPoints()).contains(new WeekPoint(new Week(2016, 9), 26L));
    assertThat(historyResource.getPoints()).contains(new WeekPoint(new Week(2016, 10), 26L));
    assertThat(historyResource.getPoints()).contains(new WeekPoint(new Week(2016, 11), 26L));
    assertThat(historyResource.getPoints()).contains(new WeekPoint(new Week(2016, 12), 26L));
    assertThat(historyResource.getPoints()).contains(new WeekPoint(new Week(2016, 13), 73L));
  }
}
