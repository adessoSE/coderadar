package io.reflectoring.coderadar.rest.analyzing;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.core.type.TypeReference;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class ListAnalyzerControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  void getAvailableAnalyzers() throws Exception {
    mvc()
        .perform(get("/analyzers"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(
            result -> {
              List<String> results =
                  fromJson(
                      new TypeReference<List<String>>() {},
                      result.getResponse().getContentAsString());
              Assertions.assertTrue(
                  results.contains("io.reflectoring.coderadar.analyzer.loc.LocAnalyzerPlugin"));
              Assertions.assertTrue(
                  results.contains(
                      "io.reflectoring.coderadar.analyzer.checkstyle.CheckstyleSourceCodeFileAnalyzerPlugin"));
            })
        .andDo(document("analyzer/list"));
  }
}
