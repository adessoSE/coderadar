package org.wickedsource.coderadar.metric.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.MetricValues.SINGLE_PROJECT_WITH_METRICS;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.containsResource;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.status;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.test.web.servlet.MvcResult;
import org.wickedsource.coderadar.testframework.category.ControllerTest;
import org.wickedsource.coderadar.testframework.template.ControllerTestTemplate;
import org.wickedsource.coderadar.testframework.template.JsonHelper;

@Category(ControllerTest.class)
public class MetricsControllerTest extends ControllerTestTemplate {

  @Test
  @DatabaseSetup(SINGLE_PROJECT_WITH_METRICS)
  @ExpectedDatabase(SINGLE_PROJECT_WITH_METRICS)
  public void listMetrics() throws Exception {
    MvcResult result =
        mvc()
            .perform(get("/projects/1/metrics"))
            .andExpect(status().isOk())
            .andExpect(containsResource(List.class))
            .andDo(document("metrics/list"))
            .andReturn();

    List<MetricResource> resources =
        JsonHelper.fromJson(result.getResponse().getContentAsString(), List.class);
    assertThat(resources).isNotNull();
    assertThat(resources.size() == 4);
  }
}
