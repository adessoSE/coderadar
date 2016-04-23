package org.wickedsource.coderadar.analyzer.checkstyle;

import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.api.FileAnalyzerPlugin;

import java.util.Iterator;
import java.util.ServiceLoader;

public class ServiceLoaderTest {

    @Test
    public void analyzerIsRegisteredWithServiceLoader(){
        ServiceLoader<FileAnalyzerPlugin> loader = ServiceLoader.load(FileAnalyzerPlugin.class);
        Iterator<FileAnalyzerPlugin> plugins = loader.iterator();
        FileAnalyzerPlugin plugin = plugins.next();
        Assert.assertTrue(plugin instanceof CheckstyleFileAnalyzerPlugin);
    }
}
