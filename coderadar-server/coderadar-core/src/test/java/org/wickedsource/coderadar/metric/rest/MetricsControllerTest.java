package org.wickedsource.coderadar.metric.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.MetricValues.SINGLE_PROJECT_WITH_METRICS;
import static org.wickedsource.coderadar.testframework.template.JsonHelper.fromPagedResourceJson;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.containsPagedResources;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.status;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.PagedResources;
import org.springframework.test.web.servlet.MvcResult;
import org.wickedsource.coderadar.testframework.template.ControllerTestTemplate;

public class MetricsControllerTest extends ControllerTestTemplate {

  @Test
  @DatabaseSetup(SINGLE_PROJECT_WITH_METRICS)
  @ExpectedDatabase(SINGLE_PROJECT_WITH_METRICS)
  public void listMetrics() throws Exception {
    MvcResult result =
        mvc()
            .perform(get("/projects/1/metrics"))
            .andExpect(status().isOk())
            .andExpect(containsPagedResources(MetricResource.class))
            .andDo(document("metrics/list"))
            .andReturn();

    PagedResources<MetricResource> resources =
        fromPagedResourceJson(result.getResponse().getContentAsString(), MetricResource.class);
    assertThat(resources).isNotNull();
    assertThat(resources.getContent()).hasSize(4);
  }
}
