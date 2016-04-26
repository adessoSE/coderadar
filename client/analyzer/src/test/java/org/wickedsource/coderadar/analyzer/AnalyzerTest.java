package org.wickedsource.coderadar.analyzer;

import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.api.AnalyzerException;
import org.wickedsource.coderadar.analyzer.api.FileSetMetrics;
import org.wickedsource.coderadar.analyzer.match.FileMatchingPattern;

import java.nio.file.Paths;
import java.util.Properties;

public class AnalyzerTest {

    @Test
    public void analyzeCoderadarSources() throws AnalyzerException {

        Properties pluginProperties = new Properties();
        pluginProperties.put("org.wickedsource.coderadar.analyzer.checkstyle.CheckstyleFileAnalyzerPlugin.enabled", Boolean.TRUE.toString());
        pluginProperties.put("org.wickedsource.coderadar.analyzer.checkstyle.CheckstyleFileAnalyzerPlugin.configLocation", "src/test/resources/checkstyle.xml");
        pluginProperties.put("org.wickedsource.coderadar.analyzer.loc.LocFileAnalyzerPlugin.enabled", Boolean.TRUE.toString());
        pluginProperties.put("org.wickedsource.coderadar.analyzer.todo.TodoFileAnalyzerPlugin.enabled", Boolean.TRUE.toString());

        AnalyzerConfiguration config = new AnalyzerConfiguration();
        FileMatchingPattern sourceCodeFiles = new FileMatchingPattern(Paths.get("src/main/java"));
        sourceCodeFiles.addIncludePattern("**/*.java");
        config.setSourceCodeFiles(sourceCodeFiles);
        config.setPluginProperties(pluginProperties);

        Analyzer analyzer = new Analyzer();
        FileSetMetrics metrics = analyzer.analyze(config);

        Assert.assertNotNull(metrics);
        Assert.assertTrue(metrics.getFiles().size() > 0);

    }

}