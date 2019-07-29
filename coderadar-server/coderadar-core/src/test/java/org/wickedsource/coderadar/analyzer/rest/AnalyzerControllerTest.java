package org.wickedsource.coderadar.analyzer.rest;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import org.junit.jupiter.api.Test;
import org.wickedsource.coderadar.testframework.template.ControllerTestTemplate;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.EMPTY;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.containsResource;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.status;

public class AnalyzerControllerTest extends ControllerTestTemplate {

  @Test
  @DatabaseSetup(EMPTY)
  @ExpectedDatabase(EMPTY)
  public void listAnalyzers() throws Exception {
    mvc()
        .perform(get("/analyzers?page=0&size=2"))
        .andExpect(status().isOk())
        .andExpect(containsResource(List.class))
        .andDo(document("analyzer/list"));
  }
}
