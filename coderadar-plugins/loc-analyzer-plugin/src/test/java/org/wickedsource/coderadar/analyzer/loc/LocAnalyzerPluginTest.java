package org.wickedsource.coderadar.analyzer.loc;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.wickedsource.coderadar.plugin.FileMetrics;
import org.wickedsource.coderadar.plugin.Metric;

import java.io.InputStream;

public class LocAnalyzerPluginTest {

  @Test
  public void metricIsCalculatedCorrectly() throws Exception {

    InputStream in = getClass().getResourceAsStream("AcceptedJavaFile.txt");
    byte[] fileContent = IOUtils.toByteArray(in);

    LocAnalyzerPlugin analyzer = new LocAnalyzerPlugin();
    FileMetrics results = analyzer.analyzeFile("TestFile.java", fileContent);

    Assertions.assertEquals(
        20, (long) results.getMetricCount(new Metric("coderadar:size:loc:java")));
  }
}
