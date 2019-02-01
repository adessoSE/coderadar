package org.wickedsource.coderadar.analyzerconfig.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.AnalyzerConfiguration.SINGLE_PROJECT_WITH_ANALYZER_CONFIGURATION;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.AnalyzerConfiguration.SINGLE_PROJECT_WITH_ANALYZER_CONFIGURATION_FILE;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.status;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.wickedsource.coderadar.testframework.template.ControllerTestTemplate;

@Tag("ControllerTest.class")
public class AnalyzerConfigurationFileControllerTest extends ControllerTestTemplate {

  @Test
  @DatabaseSetup(SINGLE_PROJECT_WITH_ANALYZER_CONFIGURATION)
  @ExpectedDatabase(SINGLE_PROJECT_WITH_ANALYZER_CONFIGURATION_FILE)
  public void uploadConfigurationFile() throws Exception {
    MockMultipartFile file =
        new MockMultipartFile("file", "config.txt", "text/plain", ("abc".getBytes()));
    mvc()
        .perform(fileUpload("/projects/1/analyzers/1/file").file(file))
        .andExpect(status().isOk())
        .andDo(document("analyzerConfigurationFile/upload"));
  }

  @Test
  @DatabaseSetup(SINGLE_PROJECT_WITH_ANALYZER_CONFIGURATION_FILE)
  @ExpectedDatabase(SINGLE_PROJECT_WITH_ANALYZER_CONFIGURATION_FILE)
  public void downloadConfigurationFile() throws Exception {
    mvc()
        .perform(get("/projects/1/analyzers/1/file"))
        .andExpect(status().isOk())
        .andExpect(content().bytes("abc".getBytes()))
        .andDo(document("analyzerConfigurationFile/download"));
  }
}
