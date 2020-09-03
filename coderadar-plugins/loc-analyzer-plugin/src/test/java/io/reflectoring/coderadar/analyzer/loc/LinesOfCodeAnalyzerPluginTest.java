package io.reflectoring.coderadar.analyzer.loc;

import io.reflectoring.coderadar.plugin.api.FileMetrics;
import io.reflectoring.coderadar.plugin.api.Metric;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LinesOfCodeAnalyzerPluginTest {

  @Test
  void metricIsCalculatedCorrectly() throws Exception {

    InputStream in = getClass().getResourceAsStream("AcceptedJavaFile.txt");
    byte[] fileContent = IOUtils.toByteArray(in);

    LocAnalyzerPlugin analyzer = new LocAnalyzerPlugin();
    FileMetrics results = analyzer.analyzeFile("TestFile.java", fileContent);

    Assertions.assertEquals(
        20, (long) results.getMetricCount(new Metric("coderadar:size:loc:java")));
  }
}
