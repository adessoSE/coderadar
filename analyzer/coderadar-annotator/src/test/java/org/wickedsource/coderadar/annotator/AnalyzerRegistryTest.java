package org.wickedsource.coderadar.annotator;

import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.api.Analyzer;
import org.wickedsource.coderadar.analyzer.api.AnalyzerConfigurationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AnalyzerRegistryTest {

    @Test
    public void dummyAnalyzersAreRegistered() throws AnalyzerConfigurationException {
        AnalyzerRegistry registry = new AnalyzerRegistry();
        registry.initializeAnalyzers(new Properties());
        List<Analyzer> analyzers = registry.getRegisteredAnalyzers();

        // assert the correct number of registered analyzers (at least the two Dummy implementations
        Assert.assertNotNull(analyzers);
        Assert.assertTrue(analyzers.size() >= 2);

        // assert that the correct analyzers are registered
        List<Class> analyzerClasses = new ArrayList<>();
        for (Analyzer analyzer : analyzers) {
            analyzerClasses.add(analyzer.getClass());
        }
        Assert.assertTrue(analyzerClasses.contains(DummyAnalyzer1.class));
        Assert.assertTrue(analyzerClasses.contains(DummyAnalyzer2.class));
    }

    @Test
    public void dummyAnalyzersAreConfiguredCorrected() throws AnalyzerConfigurationException {
        Properties properties = new Properties();
        properties.setProperty(DummyAnalyzer1.class.getName() + ".testProperty1", "value1");
        properties.setProperty(DummyAnalyzer1.class.getName() + ".testProperty2", "value2");
        properties.setProperty(DummyAnalyzer2.class.getName() + ".testProperty3", "value3");
        properties.setProperty("ignoredProperty", "ignored");

        AnalyzerRegistry registry = new AnalyzerRegistry();
        registry.initializeAnalyzers(properties);

        // assert that DummyAnalyzer2 is configured correctly
        DummyAnalyzer1 analyzer1 = (DummyAnalyzer1) getSpecificAnalyzer(registry, DummyAnalyzer1.class);
        Assert.assertEquals("value1", analyzer1.getProperties().getProperty(DummyAnalyzer1.class.getName() + ".testProperty1"));
        Assert.assertEquals("value2", analyzer1.getProperties().getProperty(DummyAnalyzer1.class.getName() + ".testProperty2"));
        Assert.assertFalse(analyzer1.getProperties().contains(DummyAnalyzer2.class.getName() + ".testProperty3"));
        Assert.assertFalse(analyzer1.getProperties().contains("ignoredProperty"));

        // assert that DummyAnalyzer2 is configured correctly
        DummyAnalyzer2 analyzer2 = (DummyAnalyzer2) getSpecificAnalyzer(registry, DummyAnalyzer2.class);
        Assert.assertFalse(analyzer2.getProperties().contains(DummyAnalyzer1.class.getName() + ".testProperty1"));
        Assert.assertFalse(analyzer2.getProperties().contains(DummyAnalyzer1.class.getName() + ".testProperty2"));
        Assert.assertEquals("value3", analyzer2.getProperties().getProperty(DummyAnalyzer2.class.getName() + ".testProperty3"));
        Assert.assertFalse(analyzer2.getProperties().contains("ignoredProperty"));

    }

    private Analyzer getSpecificAnalyzer(AnalyzerRegistry registry, Class<? extends Analyzer> analyzerClass) {
        for (Analyzer analyzer : registry.getRegisteredAnalyzers()) {
            if (analyzer.getClass() == analyzerClass) {
                return analyzer;
            }
        }
        throw new IllegalArgumentException(String.format("Analyzer %s not found", analyzerClass));
    }

}