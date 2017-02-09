package org.wickedsource.coderadar.analyzer.checkstyle;

import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.api.AnalyzerException;
import org.wickedsource.coderadar.analyzer.api.FileMetrics;
import org.wickedsource.coderadar.analyzer.api.Metric;

public class CheckstyleSourceCodeFileAnalyzerPluginTest {

  @Test
  public void metricsAreCalculatedCorrectly() throws AnalyzerException, IOException {

    byte[] fileContent =
        IOUtils.toByteArray(getClass().getResourceAsStream("/CheckstyleAnalyzer.java.txt"));

    CheckstyleSourceCodeFileAnalyzerPlugin analyzer = new CheckstyleSourceCodeFileAnalyzerPlugin();
    FileMetrics metrics = analyzer.analyzeFile("abc", fileContent);

    Assert.assertEquals(
        Long.valueOf(11L),
        metrics.getMetricCount(
            new Metric(
                "checkstyle:com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck")));
  }

  @Test
  public void cyclomaticComplexityIsCalculatedCorrectly() throws IOException {
    byte[] fileContent =
        IOUtils.toByteArray(getClass().getResourceAsStream("/AntPathMatcher.java.txt"));

    CheckstyleSourceCodeFileAnalyzerPlugin analyzer = new CheckstyleSourceCodeFileAnalyzerPlugin();
    FileMetrics metrics = analyzer.analyzeFile("abc", fileContent);

    Assert.assertEquals(
        Long.valueOf(78L),
        metrics.getMetricCount(
            new Metric(
                "checkstyle:com.puppycrawl.tools.checkstyle.checks.metrics.CyclomaticComplexityCheck")));
  }
}
