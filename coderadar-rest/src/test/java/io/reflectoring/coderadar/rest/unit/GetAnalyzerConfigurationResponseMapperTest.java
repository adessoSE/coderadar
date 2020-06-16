package io.reflectoring.coderadar.rest.unit;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.rest.GetAnalyzerConfigurationResponseMapper;
import io.reflectoring.coderadar.rest.domain.GetAnalyzerConfigurationResponse;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GetAnalyzerConfigurationResponseMapperTest {

  @Test
  void testAnalyzerConfigurationResponseMapper() {
    List<AnalyzerConfiguration> analyzerConfigurations = new ArrayList<>();
    analyzerConfigurations.add(new AnalyzerConfiguration(1L, "testAnalyzer1", true));
    analyzerConfigurations.add(new AnalyzerConfiguration(2L, "testAnalyzer2", false));

    List<GetAnalyzerConfigurationResponse> responses =
        GetAnalyzerConfigurationResponseMapper.mapAnalyzerConfigurations(analyzerConfigurations);
    Assertions.assertEquals(2L, responses.size());

    Assertions.assertEquals("testAnalyzer1", responses.get(0).getAnalyzerName());
    Assertions.assertEquals(1L, responses.get(0).getId());
    Assertions.assertTrue(responses.get(0).isEnabled());
    Assertions.assertEquals("testAnalyzer2", responses.get(1).getAnalyzerName());
    Assertions.assertEquals(2L, responses.get(1).getId());
    Assertions.assertFalse(responses.get(1).isEnabled());
  }

  @Test
  void testModuleResponseSingleMapper() {
    AnalyzerConfiguration analyzerConfiguration =
        new AnalyzerConfiguration(1L, "testAnalyzer1", true);
    GetAnalyzerConfigurationResponse response =
        GetAnalyzerConfigurationResponseMapper.mapAnalyzerConfiguration(analyzerConfiguration);
    Assertions.assertEquals("testAnalyzer1", response.getAnalyzerName());
    Assertions.assertEquals(1L, response.getId());
    Assertions.assertTrue(response.isEnabled());
  }
}
