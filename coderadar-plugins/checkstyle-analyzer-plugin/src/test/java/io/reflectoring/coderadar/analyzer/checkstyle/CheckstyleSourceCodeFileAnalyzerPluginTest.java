package io.reflectoring.coderadar.analyzer.checkstyle;

import io.reflectoring.coderadar.plugin.api.AnalyzerException;
import io.reflectoring.coderadar.plugin.api.FileMetrics;
import io.reflectoring.coderadar.plugin.api.Metric;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CheckstyleSourceCodeFileAnalyzerPluginTest {

  @Test
  public void metricsAreCalculatedCorrectly() throws AnalyzerException, IOException {

    byte[] fileContent =
        IOUtils.toByteArray(getClass().getResourceAsStream("/CheckstyleAnalyzer.java.txt"));

    CheckstyleSourceCodeFileAnalyzerPlugin analyzer = new CheckstyleSourceCodeFileAnalyzerPlugin();
    FileMetrics metrics = analyzer.analyzeFile("abc", fileContent);

    Assertions.assertEquals(
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

    Assertions.assertEquals(
        Long.valueOf(78L),
        metrics.getMetricCount(
            new Metric(
                "checkstyle:com.puppycrawl.tools.checkstyle.checks.metrics.CyclomaticComplexityCheck")));
  }
}
