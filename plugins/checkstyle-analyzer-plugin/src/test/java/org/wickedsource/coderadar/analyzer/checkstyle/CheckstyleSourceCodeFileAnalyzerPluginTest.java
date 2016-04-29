package org.wickedsource.coderadar.analyzer.checkstyle;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.api.AnalyzerException;
import org.wickedsource.coderadar.analyzer.api.FileMetrics;
import org.wickedsource.coderadar.analyzer.api.Metric;

import java.io.IOException;
import java.util.Properties;

public class CheckstyleSourceCodeFileAnalyzerPluginTest {

    @Test
    public void metricsAreCalculatedCorrectly() throws AnalyzerException, IOException {

        Properties properties = new Properties();
        properties.setProperty(CheckstyleSourceCodeFileAnalyzerPlugin.class.getName() + ".configLocation", "src/test/resources/checkstyle.xml");

        byte[] fileContent = IOUtils.toByteArray(getClass().getResourceAsStream("/CheckstyleAnalyzer.java.txt"));

        CheckstyleSourceCodeFileAnalyzerPlugin analyzer = new CheckstyleSourceCodeFileAnalyzerPlugin();
        analyzer.configure(properties);
        FileMetrics metrics = analyzer.analyzeFile(fileContent);

        Assert.assertEquals(Long.valueOf(5l), metrics.getMetricCount(new Metric("checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck")));
    }
}