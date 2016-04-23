package org.wickedsource.coderadar.annotator.annotate;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.api.CommitMetrics;
import org.wickedsource.coderadar.analyzer.api.FileMetrics;
import org.wickedsource.coderadar.analyzer.api.Finding;
import org.wickedsource.coderadar.analyzer.api.Metric;
import org.wickedsource.coderadar.annotator.serialize.GsonFactory;

public class GsonFactoryTest {

    @Test
    public void metricsAreSerializedCorrectly() {
        CommitMetrics commitMetrics = new CommitMetrics();

        Metric metric1 = new Metric("123");
        Metric metric2 = new Metric("321");
        FileMetrics metrics1 = new FileMetrics();
        metrics1.setMetricCount(metric1, 5l);
        metrics1.setMetricCount(metric2, 10l);
        metrics1.addFinding(metric1, new Finding(4, 5));
        commitMetrics.addMetricsToFile("file1", metrics1);

        FileMetrics metrics2 = new FileMetrics();
        metrics2.setMetricCount(metric2, 12l);
        metrics2.addFinding(metric2, new Finding(1, 2, 3, 4));
        commitMetrics.addMetricsToFile("file2", metrics2);

        Gson gson = GsonFactory.getInstance().createGson();

        // test java to json
        String json = gson.toJson(commitMetrics);
        Assert.assertEquals("{\"metrics\":{\"file2\":{\"counts\":{\"321\":12},\"findings\":{\"321\":[{\"lineStart\":1,\"lineEnd\":2,\"charStart\":3,\"charEnd\":4}]}},\"file1\":{\"counts\":{\"123\":5,\"321\":10},\"findings\":{\"123\":[{\"lineStart\":4,\"lineEnd\":5}]}}}}", json);

        // test json to java
        CommitMetrics fromJson = gson.fromJson(json, CommitMetrics.class);
        Assert.assertEquals(commitMetrics, fromJson);

    }
}
