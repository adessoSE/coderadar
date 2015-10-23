package org.wickedsource.coderadar.analyzer;

import org.eclipse.jgit.diff.DiffEntry;
import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.analyze.FileSetMetrics;
import org.wickedsource.coderadar.analyzer.plugin.api.FileMetrics;
import org.wickedsource.coderadar.analyzer.plugin.api.Metric;
import org.wickedsource.coderadar.analyzer.plugin.api.MetricType;
import org.wickedsource.coderadar.analyzer.walk.FileMetricsWithChangeType;

public class FileSetMetricsTest {

    private static final Metric METRIC1 = new Metric("metric1", MetricType.INTEGER);

    private static final Metric METRIC2 = new Metric("metric2", MetricType.INTEGER);

    @Test
    public void testAddMetricsToFile() throws Exception {
        FileMetricsWithChangeType fileMetrics1 = new FileMetricsWithChangeType(DiffEntry.ChangeType.ADD);
        fileMetrics1.setMetricValue(METRIC1, 500l);
        fileMetrics1.setMetricValue(METRIC2, 250l);

        FileMetricsWithChangeType fileMetrics2 = new FileMetricsWithChangeType(DiffEntry.ChangeType.ADD);
        fileMetrics2.setMetricValue(METRIC1, 300l);
        FileSetMetrics fileSetMetrics = new FileSetMetrics();

        fileSetMetrics.addMetricsToFile("file1", fileMetrics1);
        fileSetMetrics.addMetricsToFile("file1", fileMetrics2);

        FileMetrics file1Metrics = fileSetMetrics.getFileMetrics("file1");
        Assert.assertEquals(800, (long) file1Metrics.getMetricValue(METRIC1));
        Assert.assertEquals(250, (long) file1Metrics.getMetricValue(METRIC2));
    }
}