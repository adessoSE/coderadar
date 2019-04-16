package org.wickedsource.coderadar.analyzer.checkstyle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.wickedsource.coderadar.plugin.SourceCodeFileAnalyzerPlugin;

import java.util.Iterator;
import java.util.ServiceLoader;

public class ServiceLoaderTest {

    @Test
    public void analyzerIsRegisteredWithServiceLoader() {
        ServiceLoader<SourceCodeFileAnalyzerPlugin> loader =
                ServiceLoader.load(SourceCodeFileAnalyzerPlugin.class);
        Iterator<SourceCodeFileAnalyzerPlugin> plugins = loader.iterator();
        SourceCodeFileAnalyzerPlugin plugin = plugins.next();
        Assertions.assertTrue(plugin instanceof CheckstyleSourceCodeFileAnalyzerPlugin);
    }
}
