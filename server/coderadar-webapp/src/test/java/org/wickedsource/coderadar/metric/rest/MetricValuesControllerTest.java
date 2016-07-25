package org.wickedsource.coderadar.metric.rest;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.wickedsource.coderadar.metric.rest.metricvalue.CommitMetricsResource;
import org.wickedsource.coderadar.metric.rest.metricvalue.QueryParams;
import org.wickedsource.coderadar.testframework.category.ControllerTest;
import org.wickedsource.coderadar.testframework.template.ControllerTestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.MetricValues.SINGLE_PROJECT_WITH_METRICS;
import static org.wickedsource.coderadar.testframework.template.JsonHelper.fromJson;
import static org.wickedsource.coderadar.testframework.template.JsonHelper.toJsonWithoutLinks;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.containsResource;

@Category(ControllerTest.class)
public class MetricValuesControllerTest extends ControllerTestTemplate {


    @Test
    @DatabaseSetup(SINGLE_PROJECT_WITH_METRICS)
    @ExpectedDatabase(SINGLE_PROJECT_WITH_METRICS)
    public void getCommitMetrics() throws Exception {

        QueryParams params = new QueryParams();
        params.addCommitNames("abc", "def");
        params.addMetricNames("metric1", "metric3", "metric4");

        ConstrainedFields fields = fields(QueryParams.class);

        MvcResult result = mvc().perform(get("/projects/1/metricvalues/perCommit")
                .content(toJsonWithoutLinks(params))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(containsResource(CommitMetricsResource.class))
                .andDo(document("metrics/commit",
                        requestFields(
                                fields.withPath("commitNames").description("List of the names of the commits whose metrics you want to get."),
                                fields.withPath("metricNames").description("List of the names of the metrics you want get."))))
                .andReturn();

        CommitMetricsResource commitMetricsResource = fromJson(result.getResponse().getContentAsString(), CommitMetricsResource.class);
        assertThat(commitMetricsResource.getCommits()).hasSize(2);
        assertThat(commitMetricsResource.getMetricsForCommit("abc")).hasSize(3);
        assertThat(commitMetricsResource.getValueForCommitAndMetric("abc", "metric1")).isEqualTo(2);
        assertThat(commitMetricsResource.getValueForCommitAndMetric("abc", "metric3")).isEqualTo(2);
        assertThat(commitMetricsResource.getValueForCommitAndMetric("abc", "metric4")).isEqualTo(2);
        assertThat(commitMetricsResource.getMetricsForCommit("def")).hasSize(1);
        assertThat(commitMetricsResource.getValueForCommitAndMetric("def", "metric1")).isEqualTo(6);
    }

}