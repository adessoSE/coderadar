package org.wickedsource.coderadar.metric.rest;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.wickedsource.coderadar.metric.rest.metricvalue.MetricOutputsResource;
import org.wickedsource.coderadar.metric.rest.metricvalue.QueryParams;
import org.wickedsource.coderadar.testframework.category.ControllerTest;
import org.wickedsource.coderadar.testframework.template.ControllerTestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.MetricValues.SINGLE_PROJECT_WITH_METRICS;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.MetricValues.SINGLE_PROJECT_WITH_METRICS_AND_QUALITY_PROFILES;
import static org.wickedsource.coderadar.testframework.template.JsonHelper.fromJson;
import static org.wickedsource.coderadar.testframework.template.JsonHelper.toJsonWithoutLinks;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.containsResource;

@Category(ControllerTest.class)
public class MetricValuesControllerTest extends ControllerTestTemplate {


    @Test
    @DatabaseSetup(SINGLE_PROJECT_WITH_METRICS)
    @ExpectedDatabase(SINGLE_PROJECT_WITH_METRICS)
    public void queryCommitMetrics() throws Exception {

        QueryParams params = new QueryParams();
        params.getSubjects().addCommits("f9b0553930f133490f292a9c78d0b36b39be8758", "eb7cbd429530dc26d06a9ea2a62794421dce1e9a");
        params.getOutputs().addMetrics("metric1", "metric3", "metric4");

        ConstrainedFields requestFields = fields(QueryParams.class);

        MvcResult result = mvc().perform(get("/projects/1/metricvalues")
                .content(toJsonWithoutLinks(params))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(containsResource(MetricOutputsResource.class))
                .andDo(document("metrics/commit/metrics",
                        requestFields(
                                requestFields.withPath("subjects").description("Within the subjects associative array you can define for which items you want to get metric values."),
                                requestFields.withPath("subjects.commits").description("List of the names of the commits whose metrics you want to get."),
                                requestFields.withPath("outputs").description("Within the outputs associative array you can define which metrics you want to get for the subjects defined above."),
                                requestFields.withPath("outputs.metrics").description("List of the names of the metrics whose values you want to retrieve."))))
                .andReturn();

        MetricOutputsResource metricOutputsResource = fromJson(result.getResponse().getContentAsString(), MetricOutputsResource.class);
        assertThat(metricOutputsResource.getCommits()).hasSize(2);
        assertThat(metricOutputsResource.getCommits().get("f9b0553930f133490f292a9c78d0b36b39be8758").getMetrics()).hasSize(3);
        assertThat(metricOutputsResource.getCommits().get("f9b0553930f133490f292a9c78d0b36b39be8758").getMetrics().get("metric1")).isEqualTo(2);
        assertThat(metricOutputsResource.getCommits().get("f9b0553930f133490f292a9c78d0b36b39be8758").getMetrics().get("metric3")).isEqualTo(2);
        assertThat(metricOutputsResource.getCommits().get("f9b0553930f133490f292a9c78d0b36b39be8758").getMetrics().get("metric4")).isEqualTo(2);
        assertThat(metricOutputsResource.getCommits().get("eb7cbd429530dc26d06a9ea2a62794421dce1e9a").getMetrics()).hasSize(3);
        assertThat(metricOutputsResource.getCommits().get("eb7cbd429530dc26d06a9ea2a62794421dce1e9a").getMetrics().get("metric1")).isEqualTo(6);
    }

    @Test
    @DatabaseSetup(SINGLE_PROJECT_WITH_METRICS_AND_QUALITY_PROFILES)
    @ExpectedDatabase(SINGLE_PROJECT_WITH_METRICS_AND_QUALITY_PROFILES)
    public void queryCommitProfiles() throws Exception {

        QueryParams params = new QueryParams();
        params.getSubjects().addCommits("f9b0553930f133490f292a9c78d0b36b39be8758", "eb7cbd429530dc26d06a9ea2a62794421dce1e9a");
        params.getOutputs().addProfiles("profile1", "profile2");

        MvcResult result = mvc().perform(get("/projects/1/metricvalues")
                .content(toJsonWithoutLinks(params))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(containsResource(MetricOutputsResource.class))
                .andDo(document("metrics/commit/profiles"))
                .andReturn();

        MetricOutputsResource metricOutputsResource = fromJson(result.getResponse().getContentAsString(), MetricOutputsResource.class);
        assertThat(metricOutputsResource.getCommits()).hasSize(2);
        assertThat(metricOutputsResource.getCommits().get("f9b0553930f133490f292a9c78d0b36b39be8758").getProfiles()).hasSize(2);
        assertThat(metricOutputsResource.getCommits().get("f9b0553930f133490f292a9c78d0b36b39be8758").getProfiles().get("profile1").getComplianceRating()).isEqualTo(2);
        assertThat(metricOutputsResource.getCommits().get("f9b0553930f133490f292a9c78d0b36b39be8758").getProfiles().get("profile1").getViolationRating()).isEqualTo(2);
        assertThat(metricOutputsResource.getCommits().get("f9b0553930f133490f292a9c78d0b36b39be8758").getProfiles().get("profile2").getComplianceRating()).isEqualTo(2);
        assertThat(metricOutputsResource.getCommits().get("f9b0553930f133490f292a9c78d0b36b39be8758").getProfiles().get("profile2").getViolationRating()).isEqualTo(2);
        assertThat(metricOutputsResource.getCommits().get("eb7cbd429530dc26d06a9ea2a62794421dce1e9a").getProfiles()).hasSize(2);
        assertThat(metricOutputsResource.getCommits().get("eb7cbd429530dc26d06a9ea2a62794421dce1e9a").getProfiles().get("profile1").getComplianceRating()).isEqualTo(2);
        assertThat(metricOutputsResource.getCommits().get("eb7cbd429530dc26d06a9ea2a62794421dce1e9a").getProfiles().get("profile1").getViolationRating()).isEqualTo(2);
        assertThat(metricOutputsResource.getCommits().get("eb7cbd429530dc26d06a9ea2a62794421dce1e9a").getProfiles().get("profile2").getComplianceRating()).isEqualTo(0);
        assertThat(metricOutputsResource.getCommits().get("eb7cbd429530dc26d06a9ea2a62794421dce1e9a").getProfiles().get("profile2").getViolationRating()).isEqualTo(0);
    }

}