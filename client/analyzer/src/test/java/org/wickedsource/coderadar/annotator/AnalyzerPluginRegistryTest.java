package org.wickedsource.coderadar.annotator;

import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.api.AnalyzerConfigurationException;
import org.wickedsource.coderadar.analyzer.api.AnalyzerPlugin;
import org.wickedsource.coderadar.analyzer.api.FileAnalyzerPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AnalyzerPluginRegistryTest {

    @Test
    public void dummyAnalyzersAreRegistered() throws AnalyzerConfigurationException {
        AnalyzerPluginRegistry registry = new AnalyzerPluginRegistry();
        registry.initializeAnalyzers(getDummyAnalyzerProperties());
        List<FileAnalyzerPlugin> analyzerPlugins = registry.getRegisteredFileAnalyzers();

        // assert the correct number of registered analyzers (at least the two Dummy implementations
        Assert.assertNotNull(analyzerPlugins);
        Assert.assertTrue(analyzerPlugins.size() >= 2);

        // assert that the correct analyzers are registered
        List<Class> analyzerClasses = new ArrayList<>();
        for (AnalyzerPlugin analyzerPlugin : analyzerPlugins) {
            analyzerClasses.add(analyzerPlugin.getClass());
        }
        Assert.assertTrue(analyzerClasses.contains(DummyFileAnalyzerPlugin1.class));
        Assert.assertTrue(analyzerClasses.contains(DummyFileAnalyzerPlugin2.class));
    }

    private Properties getDummyAnalyzerProperties(){
        Properties p = new Properties();
        p.setProperty(DummyFileAnalyzerPlugin1.class.getName() + ".enabled", Boolean.TRUE.toString());
        p.setProperty(DummyFileAnalyzerPlugin2.class.getName() + ".enabled", Boolean.TRUE.toString());
        return p;
    }

    @Test
    public void dummyAnalyzersAreConfiguredCorrectly() throws AnalyzerConfigurationException {
        Properties properties = getDummyAnalyzerProperties();
        properties.setProperty(DummyFileAnalyzerPlugin1.class.getName() + ".testProperty1", "value1");
        properties.setProperty(DummyFileAnalyzerPlugin1.class.getName() + ".testProperty2", "value2");
        properties.setProperty(DummyFileAnalyzerPlugin2.class.getName() + ".testProperty3", "value3");
        properties.setProperty("ignoredProperty", "ignored");

        AnalyzerPluginRegistry registry = new AnalyzerPluginRegistry();
        registry.initializeAnalyzers(properties);

        // assert that DummyAnalyzer2 is configured correctly
        DummyFileAnalyzerPlugin1 analyzer1 = (DummyFileAnalyzerPlugin1) getSpecificAnalyzer(registry, DummyFileAnalyzerPlugin1.class);
        Assert.assertEquals("value1", analyzer1.getProperties().getProperty(DummyFileAnalyzerPlugin1.class.getName() + ".testProperty1"));
        Assert.assertEquals("value2", analyzer1.getProperties().getProperty(DummyFileAnalyzerPlugin1.class.getName() + ".testProperty2"));
        Assert.assertFalse(analyzer1.getProperties().contains(DummyFileAnalyzerPlugin2.class.getName() + ".testProperty3"));
        Assert.assertFalse(analyzer1.getProperties().contains("ignoredProperty"));

        // assert that DummyAnalyzer2 is configured correctly
        DummyFileAnalyzerPlugin2 analyzer2 = (DummyFileAnalyzerPlugin2) getSpecificAnalyzer(registry, DummyFileAnalyzerPlugin2.class);
        Assert.assertFalse(analyzer2.getProperties().contains(DummyFileAnalyzerPlugin1.class.getName() + ".testProperty1"));
        Assert.assertFalse(analyzer2.getProperties().contains(DummyFileAnalyzerPlugin1.class.getName() + ".testProperty2"));
        Assert.assertEquals("value3", analyzer2.getProperties().getProperty(DummyFileAnalyzerPlugin2.class.getName() + ".testProperty3"));
        Assert.assertFalse(analyzer2.getProperties().contains("ignoredProperty"));

    }

    private AnalyzerPlugin getSpecificAnalyzer(AnalyzerPluginRegistry registry, Class<? extends AnalyzerPlugin> analyzerClass) {
        for (AnalyzerPlugin analyzerPlugin : registry.getRegisteredFileAnalyzers()) {
            if (analyzerPlugin.getClass() == analyzerClass) {
                return analyzerPlugin;
            }
        }
        Assert.fail(String.format("Analyzer %s was not registered!", analyzerClass));
        return null;
    }

}