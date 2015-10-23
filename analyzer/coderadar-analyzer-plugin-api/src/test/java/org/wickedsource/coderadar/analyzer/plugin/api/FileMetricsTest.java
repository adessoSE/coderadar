package org.wickedsource.coderadar.analyzer.plugin.api;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileMetricsTest {

    private static final Metric METRIC1 = new Metric("metric1");

    private static final Metric METRIC2 = new Metric("metric2");

    @Test
    public void testAdd() throws Exception {
        FileMetrics fileMetrics1 = new FileMetrics();
        fileMetrics1.setMetricValue(METRIC1, 500l);
        fileMetrics1.setMetricValue(METRIC2, 250l);

        FileMetrics fileMetrics2 = new FileMetrics();
        fileMetrics2.setMetricValue(METRIC1, 300l);

        fileMetrics1.add(fileMetrics2);

        Assert.assertEquals(800l, (long) fileMetrics1.getMetricValue(METRIC1));
        Assert.assertEquals(250l, (long) fileMetrics1.getMetricValue(METRIC2));
    }
}