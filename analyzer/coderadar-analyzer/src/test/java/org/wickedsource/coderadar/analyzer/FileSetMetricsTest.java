package org.wickedsource.coderadar.analyzer;

import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.plugin.api.FileMetrics;
import org.wickedsource.coderadar.analyzer.plugin.api.Metric;
import org.wickedsource.coderadar.analyzer.plugin.api.MetricType;

public class FileSetMetricsTest {

    private static final Metric METRIC1 = new Metric("metric1", MetricType.INTEGER);

    private static final Metric METRIC2 = new Metric("metric2", MetricType.INTEGER);

    @Test
    public void testAddMetricsToFile() throws Exception {
        FileMetrics fileMetrics1 = new FileMetrics();
        fileMetrics1.setMetricValue(METRIC1, 500l);
        fileMetrics1.setMetricValue(METRIC2, 250l);

        FileMetrics fileMetrics2 = new FileMetrics();
        fileMetrics2.setMetricValue(METRIC1, 300l);
        FileSetMetrics fileSetMetrics = new FileSetMetrics();

        fileSetMetrics.addMetricsToFile("file1", fileMetrics1);
        fileSetMetrics.addMetricsToFile("file1", fileMetrics2);


        FileMetrics file1Metrics = fileSetMetrics.getFileMetrics("file1");
        Assert.assertEquals(800, (long) file1Metrics.getMetricValue(METRIC1));
        Assert.assertEquals(250, (long) file1Metrics.getMetricValue(METRIC2));
    }
}