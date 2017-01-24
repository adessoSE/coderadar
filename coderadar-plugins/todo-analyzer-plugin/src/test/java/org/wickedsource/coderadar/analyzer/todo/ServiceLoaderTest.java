package org.wickedsource.coderadar.analyzer.todo;

import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.api.SourceCodeFileAnalyzerPlugin;

import java.util.Iterator;
import java.util.ServiceLoader;

public class ServiceLoaderTest {

    @Test
    public void analyzerIsRegisteredWithServiceLoader(){
        ServiceLoader<SourceCodeFileAnalyzerPlugin> loader = ServiceLoader.load(SourceCodeFileAnalyzerPlugin.class);
        Iterator<SourceCodeFileAnalyzerPlugin> plugins = loader.iterator();
        SourceCodeFileAnalyzerPlugin plugin = plugins.next();
        Assert.assertTrue(plugin instanceof TodoSourceCodeFileAnalyzerPlugin);
    }
}
