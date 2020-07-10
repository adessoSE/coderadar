package io.reflectoring.coderadar.analyzer.loc;

import io.reflectoring.coderadar.plugin.api.SourceCodeFileAnalyzerPlugin;
import java.util.Iterator;
import java.util.ServiceLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ServiceLoaderTest {

  @Test
  void analyzerIsRegisteredWithServiceLoader() {
    ServiceLoader<SourceCodeFileAnalyzerPlugin> loader =
        ServiceLoader.load(SourceCodeFileAnalyzerPlugin.class);
    Iterator<SourceCodeFileAnalyzerPlugin> plugins = loader.iterator();
    SourceCodeFileAnalyzerPlugin plugin = plugins.next();
    Assertions.assertTrue(plugin instanceof LocAnalyzerPlugin);
  }
}
