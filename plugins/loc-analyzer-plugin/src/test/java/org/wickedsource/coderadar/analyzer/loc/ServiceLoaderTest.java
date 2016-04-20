package org.wickedsource.coderadar.analyzer.loc;

import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.api.AnalyzerPlugin;

import java.util.Iterator;
import java.util.ServiceLoader;

public class ServiceLoaderTest {

    @Test
    public void analyzerIsRegisteredWithServiceLoader(){
        ServiceLoader<AnalyzerPlugin> loader = ServiceLoader.load(AnalyzerPlugin.class);
        Iterator<AnalyzerPlugin> plugins = loader.iterator();
        AnalyzerPlugin plugin = plugins.next();
        Assert.assertTrue(plugin instanceof LocAnalyzerPlugin);
    }
}
