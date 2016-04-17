package org.wickedsource.coderadar.analyzer.loc;

import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.api.Analyzer;

import java.util.Iterator;
import java.util.ServiceLoader;

public class ServiceLoaderTest {

    @Test
    public void analyzerIsRegisteredWithServiceLoader(){
        ServiceLoader<Analyzer> loader = ServiceLoader.load(Analyzer.class);
        Iterator<Analyzer> plugins = loader.iterator();
        Analyzer plugin = plugins.next();
        Assert.assertTrue(plugin instanceof LocAnalyzer);
    }
}
