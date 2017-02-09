package org.wickedsource.coderadar.analyzer.loc;

import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.api.FileMetrics;
import org.wickedsource.coderadar.analyzer.api.Metric;

public class LocAnalyzerPluginTest {

  @Test
  public void metricIsCalculatedCorrectly() throws Exception {

    InputStream in = getClass().getResourceAsStream("AcceptedJavaFile.txt");
    byte[] fileContent = IOUtils.toByteArray(in);

    LocAnalyzerPlugin analyzer = new LocAnalyzerPlugin();
    FileMetrics results = analyzer.analyzeFile("TestFile.java", fileContent);

    Assert.assertEquals(20, (long) results.getMetricCount(new Metric("coderadar:size:loc:java")));
  }
}
