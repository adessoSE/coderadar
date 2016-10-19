package org.wickedsource.coderadar.metricquery.rest.module;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.wickedsource.coderadar.testframework.template.ControllerTestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.Modules.SINGLE_PROJECT_WITH_METRICS_AND_MODULES;
import static org.wickedsource.coderadar.testframework.template.JsonHelper.fromJson;
import static org.wickedsource.coderadar.testframework.template.JsonHelper.toJsonWithoutLinks;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.containsResource;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.status;

public class ModuleMetricsControllerTest extends ControllerTestTemplate {

    @Test
    @DatabaseSetup(SINGLE_PROJECT_WITH_METRICS_AND_MODULES)
    public void queryCommitMetrics() throws Exception {

        ModuleMetricsQuery query = new ModuleMetricsQuery();
        query.setCommit("eb7cbd429530dc26d06a9ea2a62794421dce1e9a");
        query.addMetrics("metric1", "metric2", "metric3");

        ConstrainedFields requestFields = fields(ModuleMetricsQuery.class);

        MvcResult result = mvc().perform(get("/projects/1/metricvalues/perModule")
                .content(toJsonWithoutLinks(query))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(containsResource(MetricsTreeResource.class))
                .andDo(document("metrics/module/tree",
                        requestFields(
                                requestFields.withPath("commit").description("Name of the commit that defines the point in time at which the metrics should be queried."),
                                requestFields.withPath("metrics").description("List of the names of the metrics whose values you want to query."))))
                .andReturn();

        MetricsTreeResource metricsTreeResource = fromJson(result.getResponse().getContentAsString(), MetricsTreeResource.class);
        assertThat(metricsTreeResource.getChildren()).hasSize(2);
        assertThat(metricsTreeResource.getPayload().getMetricValue("metric1")).isEqualTo(26L + 12L);
        assertThat(metricsTreeResource.getPayload().getMetricValue("metric2")).isEqualTo(28L + 0L);
        assertThat(metricsTreeResource.getPayload().getMetricValue("metric3")).isEqualTo(14L + 5L);
        assertThat(metricsTreeResource.getChildren().get(0).getName()).isEqualTo("/path/to/module1");
        assertThat(metricsTreeResource.getChildren().get(0).getChildren()).hasSize(1);
        assertThat(metricsTreeResource.getChildren().get(0).getPayload().getMetricValue("metric1")).isEqualTo(26L);
        assertThat(metricsTreeResource.getChildren().get(0).getPayload().getMetricValue("metric2")).isEqualTo(28L);
        assertThat(metricsTreeResource.getChildren().get(0).getPayload().getMetricValue("metric3")).isEqualTo(14L);
        assertThat(metricsTreeResource.getChildren().get(0).getChildren().get(0).getName()).isEqualTo("/path/to/module1/submodule2");
        assertThat(metricsTreeResource.getChildren().get(0).getChildren().get(0).getPayload().getMetricValue("metric1")).isEqualTo(14L);
        assertThat(metricsTreeResource.getChildren().get(0).getChildren().get(0).getPayload().getMetricValue("metric2")).isEqualTo(15L);
        assertThat(metricsTreeResource.getChildren().get(0).getChildren().get(0).getPayload().getMetricValue("metric3")).isEqualTo(3L);
        assertThat(metricsTreeResource.getChildren().get(0).getChildren().get(0).getChildren()).isEmpty();
        assertThat(metricsTreeResource.getChildren().get(1).getName()).isEqualTo("/path/to/module3");
        assertThat(metricsTreeResource.getChildren().get(1).getChildren()).isEmpty();
        assertThat(metricsTreeResource.getChildren().get(1).getPayload().getMetricValue("metric1")).isEqualTo(12L);
        assertThat(metricsTreeResource.getChildren().get(1).getPayload().getMetricValue("metric2")).isNull();
        assertThat(metricsTreeResource.getChildren().get(1).getPayload().getMetricValue("metric3")).isEqualTo(5L);

    }

}