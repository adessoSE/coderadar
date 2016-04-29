package org.wickedsource.coderadar.analyzer;

import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.api.AnalyzerConfigurationException;
import org.wickedsource.coderadar.analyzer.api.AnalyzerPlugin;
import org.wickedsource.coderadar.analyzer.api.SourceCodeFileAnalyzerPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AnalyzerPluginRegistryTest {

    @Test
    public void dummyAnalyzersAreRegistered() throws AnalyzerConfigurationException {
        AnalyzerPluginRegistry registry = new AnalyzerPluginRegistry();
        registry.initializeAnalyzers(getDummyAnalyzerProperties());
        List<SourceCodeFileAnalyzerPlugin> analyzerPlugins = registry.getRegisteredFileAnalyzers();

        // assert the correct number of registered analyzers (at least the two Dummy implementations
        Assert.assertNotNull(analyzerPlugins);
        Assert.assertTrue(analyzerPlugins.size() >= 2);

        // assert that the correct analyzers are registered
        List<Class> analyzerClasses = new ArrayList<>();
        for (AnalyzerPlugin analyzerPlugin : analyzerPlugins) {
            analyzerClasses.add(analyzerPlugin.getClass());
        }
        Assert.assertTrue(analyzerClasses.contains(DummySourceCodeFileAnalyzerPlugin1.class));
        Assert.assertTrue(analyzerClasses.contains(DummySourceCodeFileAnalyzerPlugin2.class));
    }

    private Properties getDummyAnalyzerProperties(){
        Properties p = new Properties();
        p.setProperty(DummySourceCodeFileAnalyzerPlugin1.class.getName() + ".enabled", Boolean.TRUE.toString());
        p.setProperty(DummySourceCodeFileAnalyzerPlugin2.class.getName() + ".enabled", Boolean.TRUE.toString());
        return p;
    }

    @Test
    public void dummyAnalyzersAreConfiguredCorrectly() throws AnalyzerConfigurationException {
        Properties properties = getDummyAnalyzerProperties();
        properties.setProperty(DummySourceCodeFileAnalyzerPlugin1.class.getName() + ".testProperty1", "value1");
        properties.setProperty(DummySourceCodeFileAnalyzerPlugin1.class.getName() + ".testProperty2", "value2");
        properties.setProperty(DummySourceCodeFileAnalyzerPlugin2.class.getName() + ".testProperty3", "value3");
        properties.setProperty("ignoredProperty", "ignored");

        AnalyzerPluginRegistry registry = new AnalyzerPluginRegistry();
        registry.initializeAnalyzers(properties);

        // assert that DummyAnalyzer2 is configured correctly
        DummySourceCodeFileAnalyzerPlugin1 analyzer1 = (DummySourceCodeFileAnalyzerPlugin1) getSpecificAnalyzer(registry, DummySourceCodeFileAnalyzerPlugin1.class);
        Assert.assertEquals("value1", analyzer1.getProperties().getProperty(DummySourceCodeFileAnalyzerPlugin1.class.getName() + ".testProperty1"));
        Assert.assertEquals("value2", analyzer1.getProperties().getProperty(DummySourceCodeFileAnalyzerPlugin1.class.getName() + ".testProperty2"));
        Assert.assertFalse(analyzer1.getProperties().contains(DummySourceCodeFileAnalyzerPlugin2.class.getName() + ".testProperty3"));
        Assert.assertFalse(analyzer1.getProperties().contains("ignoredProperty"));

        // assert that DummyAnalyzer2 is configured correctly
        DummySourceCodeFileAnalyzerPlugin2 analyzer2 = (DummySourceCodeFileAnalyzerPlugin2) getSpecificAnalyzer(registry, DummySourceCodeFileAnalyzerPlugin2.class);
        Assert.assertFalse(analyzer2.getProperties().contains(DummySourceCodeFileAnalyzerPlugin1.class.getName() + ".testProperty1"));
        Assert.assertFalse(analyzer2.getProperties().contains(DummySourceCodeFileAnalyzerPlugin1.class.getName() + ".testProperty2"));
        Assert.assertEquals("value3", analyzer2.getProperties().getProperty(DummySourceCodeFileAnalyzerPlugin2.class.getName() + ".testProperty3"));
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