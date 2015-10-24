package org.wickedsource.coderadar.annotator.analyze;

import org.eclipse.jgit.diff.DiffEntry;
import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.api.FileMetrics;
import org.wickedsource.coderadar.analyzer.api.Metric;
import org.wickedsource.coderadar.annotator.walk.FileMetricsWithChangeType;

public class CommitMetricsTest {

    private static final Metric METRIC1 = new Metric("metric1");

    private static final Metric METRIC2 = new Metric("metric2");

    @Test
    public void testAddMetricsToFile() throws Exception {
        FileMetricsWithChangeType fileMetrics1 = new FileMetricsWithChangeType(DiffEntry.ChangeType.ADD);
        fileMetrics1.setMetricValue(METRIC1, 500l);
        fileMetrics1.setMetricValue(METRIC2, 250l);

        FileMetricsWithChangeType fileMetrics2 = new FileMetricsWithChangeType(DiffEntry.ChangeType.ADD);
        fileMetrics2.setMetricValue(METRIC1, 300l);
        CommitMetrics commitMetrics = new CommitMetrics();

        commitMetrics.addMetricsToFile("file1", fileMetrics1);
        commitMetrics.addMetricsToFile("file1", fileMetrics2);

        FileMetrics file1Metrics = commitMetrics.getFileMetrics("file1");
        Assert.assertEquals(800, (long) file1Metrics.getMetricValue(METRIC1));
        Assert.assertEquals(250, (long) file1Metrics.getMetricValue(METRIC2));
    }
}