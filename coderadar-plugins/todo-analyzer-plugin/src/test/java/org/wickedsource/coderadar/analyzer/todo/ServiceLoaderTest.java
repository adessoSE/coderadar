package org.wickedsource.coderadar.analyzer.todo;

import java.util.Iterator;
import java.util.ServiceLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.wickedsource.coderadar.analyzer.api.SourceCodeFileAnalyzerPlugin;

public class ServiceLoaderTest {

  @Test
  public void analyzerIsRegisteredWithServiceLoader() {
    ServiceLoader<SourceCodeFileAnalyzerPlugin> loader =
        ServiceLoader.load(SourceCodeFileAnalyzerPlugin.class);
    Iterator<SourceCodeFileAnalyzerPlugin> plugins = loader.iterator();
    SourceCodeFileAnalyzerPlugin plugin = plugins.next();
    Assertions.assertTrue(plugin instanceof TodoSourceCodeFileAnalyzerPlugin);
  }
}
