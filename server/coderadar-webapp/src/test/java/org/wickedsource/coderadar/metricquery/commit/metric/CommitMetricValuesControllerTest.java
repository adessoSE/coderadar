package org.wickedsource.coderadar.metricquery.commit.metric;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
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
public class CommitMetricValuesControllerTest extends ControllerTestTemplate {


    @Test
    @DatabaseSetup(SINGLE_PROJECT_WITH_METRICS)
    @ExpectedDatabase(SINGLE_PROJECT_WITH_METRICS)
    public void queryCommitMetrics() throws Exception {

        CommitMetricsQuery params = new CommitMetricsQuery();
        params.setCommit("eb7cbd429530dc26d06a9ea2a62794421dce1e9a");
        params.addMetrics("metric1", "metric2", "metric3", "metric4");

        ConstrainedFields requestFields = fields(CommitMetricsQuery.class);

        MvcResult result = mvc().perform(get("/projects/1/metricvalues/perCommit")
                .content(toJsonWithoutLinks(params))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(containsResource(CommitMetricsOutputResource.class))
                .andDo(document("metrics/commit/metrics",
                        requestFields(
                                requestFields.withPath("commit").description("Name of the commit whose metric values to get."),
                                requestFields.withPath("metrics").description("List of the names of the metrics whose values you want to query."))))
                .andReturn();

        CommitMetricsOutputResource commitMetricsOutputResource = fromJson(result.getResponse().getContentAsString(), CommitMetricsOutputResource.class);
        assertThat(commitMetricsOutputResource.getMetrics()).hasSize(4);
        assertThat(commitMetricsOutputResource.getMetrics().get("metric1")).isEqualTo(26);
        assertThat(commitMetricsOutputResource.getMetrics().get("metric2")).isEqualTo(28);
        assertThat(commitMetricsOutputResource.getMetrics().get("metric3")).isEqualTo(11);
        assertThat(commitMetricsOutputResource.getMetrics().get("metric4")).isEqualTo(12);
    }

}