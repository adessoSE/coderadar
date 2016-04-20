package org.wickedsource.coderadar.annotator;

import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.api.AnalyzerConfigurationException;
import org.wickedsource.coderadar.analyzer.api.AnalyzerPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AnalyzerPluginRegistryTest {

    @Test
    public void dummyAnalyzersAreRegistered() throws AnalyzerConfigurationException {
        AnalyzerRegistry registry = new AnalyzerRegistry();
        registry.initializeAnalyzers(getDummyAnalyzerProperties());
        List<AnalyzerPlugin> analyzerPlugins = registry.getRegisteredAnalyzers();

        // assert the correct number of registered analyzers (at least the two Dummy implementations
        Assert.assertNotNull(analyzerPlugins);
        Assert.assertTrue(analyzerPlugins.size() >= 2);

        // assert that the correct analyzers are registered
        List<Class> analyzerClasses = new ArrayList<>();
        for (AnalyzerPlugin analyzerPlugin : analyzerPlugins) {
            analyzerClasses.add(analyzerPlugin.getClass());
        }
        Assert.assertTrue(analyzerClasses.contains(DummyAnalyzerPlugin1.class));
        Assert.assertTrue(analyzerClasses.contains(DummyAnalyzerPlugin2.class));
    }

    private Properties getDummyAnalyzerProperties(){
        Properties p = new Properties();
        p.setProperty(DummyAnalyzerPlugin1.class.getName() + ".enabled", Boolean.TRUE.toString());
        p.setProperty(DummyAnalyzerPlugin2.class.getName() + ".enabled", Boolean.TRUE.toString());
        return p;
    }

    @Test
    public void dummyAnalyzersAreConfiguredCorrectly() throws AnalyzerConfigurationException {
        Properties properties = getDummyAnalyzerProperties();
        properties.setProperty(DummyAnalyzerPlugin1.class.getName() + ".testProperty1", "value1");
        properties.setProperty(DummyAnalyzerPlugin1.class.getName() + ".testProperty2", "value2");
        properties.setProperty(DummyAnalyzerPlugin2.class.getName() + ".testProperty3", "value3");
        properties.setProperty("ignoredProperty", "ignored");

        AnalyzerRegistry registry = new AnalyzerRegistry();
        registry.initializeAnalyzers(properties);

        // assert that DummyAnalyzer2 is configured correctly
        DummyAnalyzerPlugin1 analyzer1 = (DummyAnalyzerPlugin1) getSpecificAnalyzer(registry, DummyAnalyzerPlugin1.class);
        Assert.assertEquals("value1", analyzer1.getProperties().getProperty(DummyAnalyzerPlugin1.class.getName() + ".testProperty1"));
        Assert.assertEquals("value2", analyzer1.getProperties().getProperty(DummyAnalyzerPlugin1.class.getName() + ".testProperty2"));
        Assert.assertFalse(analyzer1.getProperties().contains(DummyAnalyzerPlugin2.class.getName() + ".testProperty3"));
        Assert.assertFalse(analyzer1.getProperties().contains("ignoredProperty"));

        // assert that DummyAnalyzer2 is configured correctly
        DummyAnalyzerPlugin2 analyzer2 = (DummyAnalyzerPlugin2) getSpecificAnalyzer(registry, DummyAnalyzerPlugin2.class);
        Assert.assertFalse(analyzer2.getProperties().contains(DummyAnalyzerPlugin1.class.getName() + ".testProperty1"));
        Assert.assertFalse(analyzer2.getProperties().contains(DummyAnalyzerPlugin1.class.getName() + ".testProperty2"));
        Assert.assertEquals("value3", analyzer2.getProperties().getProperty(DummyAnalyzerPlugin2.class.getName() + ".testProperty3"));
        Assert.assertFalse(analyzer2.getProperties().contains("ignoredProperty"));

    }

    private AnalyzerPlugin getSpecificAnalyzer(AnalyzerRegistry registry, Class<? extends AnalyzerPlugin> analyzerClass) {
        for (AnalyzerPlugin analyzerPlugin : registry.getRegisteredAnalyzers()) {
            if (analyzerPlugin.getClass() == analyzerClass) {
                return analyzerPlugin;
            }
        }
        Assert.fail(String.format("Analyzer %s was not registered!", analyzerClass));
        return null;
    }

}