package io.reflectoring.coderadar.graph;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzerConfigurationEntity;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.AnalyzerConfigurationMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AnalyzerConfigurationMapperTest {
  private final AnalyzerConfigurationMapper configurationMapper = new AnalyzerConfigurationMapper();

  @Test
  public void testMapDomainObject() {
    AnalyzerConfiguration testBranch =
        new AnalyzerConfiguration().setEnabled(true).setId(1L).setAnalyzerName("testName");

    AnalyzerConfigurationEntity result = configurationMapper.mapDomainObject(testBranch);
    Assertions.assertTrue(result.getEnabled());
    Assertions.assertEquals("testName", result.getAnalyzerName());
    Assertions.assertNull(result.getId());
  }

  @Test
  public void testMapGraphObject() {
    AnalyzerConfigurationEntity testBranch =
        new AnalyzerConfigurationEntity().setEnabled(true).setId(1L).setAnalyzerName("testName");

    AnalyzerConfiguration result = configurationMapper.mapGraphObject(testBranch);
    Assertions.assertTrue(result.isEnabled());
    Assertions.assertEquals("testName", result.getAnalyzerName());
    Assertions.assertEquals(1L, result.getId());
  }
}
