package org.wickedsource.coderadar.analyzer.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.wickedsource.coderadar.analyzer.service.AnalyzerPluginRegistry;
import org.wickedsource.coderadar.plugin.SourceCodeFileAnalyzerPlugin;

public class AnalyzerPluginRegistryTest {

  private static AnalyzerPluginRegistry registry =
      new AnalyzerPluginRegistry("org.wickedsource.coderadar.analyzer.domain");

  @Test
  public void createsNewPluginInstances() {
    SourceCodeFileAnalyzerPlugin plugin1 =
        registry.createAnalyzer("org.wickedsource.coderadar.analyzer.domain.DummyAnalyzer");
    SourceCodeFileAnalyzerPlugin plugin2 =
        registry.createAnalyzer("org.wickedsource.coderadar.analyzer.domain.DummyAnalyzer");
    Assertions.assertNotNull(plugin1);
    Assertions.assertNotNull(plugin2);
    Assertions.assertFalse(plugin1 == plugin2);
  }

  @Test
  public void failsOnCreatingUnregisteredAnalyzer() {
    Assertions.assertThrows(
        IllegalArgumentException.class, () -> registry.createAnalyzer("foo.bar"));
  }

  @Test
  public void isAnalyzerRegistered() {
    Assertions.assertTrue(
        registry.isAnalyzerRegistered("org.wickedsource.coderadar.analyzer.domain.DummyAnalyzer"));
    Assertions.assertFalse(registry.isAnalyzerRegistered("foo.bar"));
  }

  @Test
  public void configuresPluginsWhenConfigurationFileProvided() {
    SourceCodeFileAnalyzerPlugin plugin1 =
        registry.createAnalyzer(
            "org.wickedsource.coderadar.analyzer.domain.DummyAnalyzer", new byte[] {'a', 'b', 'c'});
    Assertions.assertTrue(((DummyAnalyzer) plugin1).isConfigured());
  }

  @Test
  public void failsOnInvalidConfigurationFile() {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            registry.createAnalyzer(
                "org.wickedsource.coderadar.analyzer.domain.DummyAnalyzer",
                new byte[] {'c', 'b', 'a'}));
  }
}
