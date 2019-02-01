package org.wickedsource.coderadar.analyzer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
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
    Assertions.assertNotNull(plugin1);
    Assertions.assertNotNull(plugin2);
    Assertions.assertFalse(plugin1 == plugin2);
  }

  @Test
  public void returnsPageableAnalyzers() {
    assertThat(registry.getAvailableAnalyzers(new PageRequest(0, 10))).hasSize(5);
    assertThat(registry.getAvailableAnalyzers(new PageRequest(0, 5))).hasSize(5);
    assertThat(registry.getAvailableAnalyzers(new PageRequest(0, 4))).hasSize(4);
    assertThat(registry.getAvailableAnalyzers(new PageRequest(1, 4))).hasSize(1);
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
