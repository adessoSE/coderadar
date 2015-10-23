package org.wickedsource.coderadar.analyzer.annotate;

import com.google.gson.Gson;
import org.eclipse.jgit.diff.DiffEntry;
import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.analyze.CommitMetrics;
import org.wickedsource.coderadar.analyzer.json.GsonFactory;
import org.wickedsource.coderadar.analyzer.plugin.api.Metric;
import org.wickedsource.coderadar.analyzer.walk.FileMetricsWithChangeType;

public class GsonFactoryTest {

    @Test
    public void test() {
        CommitMetrics commitMetrics = new CommitMetrics();

        Metric metric1 = new Metric("123");
        Metric metric2 = new Metric("321");
        FileMetricsWithChangeType metrics1 = new FileMetricsWithChangeType(DiffEntry.ChangeType.ADD);
        metrics1.setMetricValue(metric1, 5l);
        metrics1.setMetricValue(metric2, 10l);
        commitMetrics.addMetricsToFile("file1", metrics1);

        FileMetricsWithChangeType metrics2 = new FileMetricsWithChangeType(DiffEntry.ChangeType.COPY);
        metrics1.setMetricValue(metric2, 12l);
        commitMetrics.addMetricsToFile("file2", metrics2);

        Gson gson = GsonFactory.getInstance().createGson();

        // test java to json
        String json = gson.toJson(commitMetrics);
        Assert.assertEquals("{\"metrics\":{\"file2\":{\"changeType\":\"COPY\",\"metricValues\":{}},\"file1\":{\"changeType\":\"ADD\",\"metricValues\":{\"123\":5,\"321\":12}}}}", json);

        // test json to java
        CommitMetrics fromJson = gson.fromJson(json, CommitMetrics.class);
        Assert.assertEquals(commitMetrics, fromJson);

    }
}
