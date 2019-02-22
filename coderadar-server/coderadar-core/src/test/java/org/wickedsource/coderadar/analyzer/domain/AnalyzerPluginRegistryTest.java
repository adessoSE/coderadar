package org.wickedsource.coderadar.analyzer.domain;

import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.api.SourceCodeFileAnalyzerPlugin;
import org.wickedsource.coderadar.analyzer.service.AnalyzerPluginRegistry;

public class AnalyzerPluginRegistryTest {

  private static AnalyzerPluginRegistry registry =
      new AnalyzerPluginRegistry("org.wickedsource.coderadar.analyzer.domain");

  @Test
  public void createsNewPluginInstances() {
    SourceCodeFileAnalyzerPlugin plugin1 =
        registry.createAnalyzer("org.wickedsource.coderadar.analyzer.domain.DummyAnalyzer");
    SourceCodeFileAnalyzerPlugin plugin2 =
        registry.createAnalyzer("org.wickedsource.coderadar.analyzer.domain.DummyAnalyzer");
    Assert.assertNotNull(plugin1);
    Assert.assertNotNull(plugin2);
    Assert.assertFalse(plugin1 == plugin2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void failsOnCreatingUnregisteredAnalyzer() {
    registry.createAnalyzer("foo.bar");
  }

  @Test
  public void isAnalyzerRegistered() {
    Assert.assertTrue(
        registry.isAnalyzerRegistered("org.wickedsource.coderadar.analyzer.domain.DummyAnalyzer"));
    Assert.assertFalse(registry.isAnalyzerRegistered("foo.bar"));
  }

  @Test
  public void configuresPluginsWhenConfigurationFileProvided() {
    SourceCodeFileAnalyzerPlugin plugin1 =
        registry.createAnalyzer(
            "org.wickedsource.coderadar.analyzer.domain.DummyAnalyzer", new byte[] {'a', 'b', 'c'});
    Assert.assertTrue(((DummyAnalyzer) plugin1).isConfigured());
  }

  @Test(expected = IllegalArgumentException.class)
  public void failsOnInvalidConfigurationFile() {
    SourceCodeFileAnalyzerPlugin plugin1 =
        registry.createAnalyzer(
            "org.wickedsource.coderadar.analyzer.domain.DummyAnalyzer", new byte[] {'c', 'b', 'a'});
  }
}
