package org.wickedsource.coderadar.analyzer.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileMetricsTest {

  private static final Metric METRIC1 = new Metric("metric1");

  private static final Metric METRIC2 = new Metric("metric2");

  @Test
  public void testAdd() throws Exception {
    FileMetrics fileMetrics1 = new FileMetrics();
    fileMetrics1.setMetricCount(METRIC1, 500L);
    fileMetrics1.setMetricCount(METRIC2, 250L);

    FileMetrics fileMetrics2 = new FileMetrics();
    fileMetrics2.setMetricCount(METRIC1, 300L);

    fileMetrics1.add(fileMetrics2);

    Assertions.assertEquals(800L, (long) fileMetrics1.getMetricCount(METRIC1));
    Assertions.assertEquals(250L, (long) fileMetrics1.getMetricCount(METRIC2));
  }
}
