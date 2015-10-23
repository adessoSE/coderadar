package org.wickedsource.coderadar.analyzer.annotate;

import com.google.gson.Gson;
import org.eclipse.jgit.diff.DiffEntry;
import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.analyze.FileSetMetrics;
import org.wickedsource.coderadar.analyzer.json.GsonFactory;
import org.wickedsource.coderadar.analyzer.plugin.api.Metric;
import org.wickedsource.coderadar.analyzer.walk.FileMetricsWithChangeType;

public class GsonFactoryTest {

    @Test
    public void test() {
        FileSetMetrics fileSetMetrics = new FileSetMetrics();

        Metric metric1 = new Metric("123");
        Metric metric2 = new Metric("321");
        FileMetricsWithChangeType metrics1 = new FileMetricsWithChangeType(DiffEntry.ChangeType.ADD);
        metrics1.setMetricValue(metric1, 5l);
        metrics1.setMetricValue(metric2, 10l);
        fileSetMetrics.addMetricsToFile("file1", metrics1);

        FileMetricsWithChangeType metrics2 = new FileMetricsWithChangeType(DiffEntry.ChangeType.COPY);
        metrics1.setMetricValue(metric2, 12l);
        fileSetMetrics.addMetricsToFile("file2", metrics2);

        Gson gson = GsonFactory.getInstance().createGson();

        // test java to json
        String json = gson.toJson(fileSetMetrics);
        Assert.assertEquals("{\"metrics\":{\"file2\":{\"changeType\":\"COPY\",\"metricValues\":{}},\"file1\":{\"changeType\":\"ADD\",\"metricValues\":{\"123\":5,\"321\":12}}}}", json);

        // test json to java
        FileSetMetrics fromJson = gson.fromJson(json, FileSetMetrics.class);
        Assert.assertEquals(fileSetMetrics, fromJson);

    }
}
