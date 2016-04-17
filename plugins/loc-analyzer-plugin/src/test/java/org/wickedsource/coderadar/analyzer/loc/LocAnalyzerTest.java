package org.wickedsource.coderadar.analyzer.loc;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.api.FileMetrics;

import java.io.InputStream;

public class LocAnalyzerTest {

    @Test
    public void metricIsCalculatedCorrectly() throws Exception {

        InputStream in = getClass().getResourceAsStream("AcceptedJavaFile.txt");
        byte[] fileContent = IOUtils.toByteArray(in);

        LocAnalyzer analyzer = new LocAnalyzer();
        FileMetrics results = analyzer.analyzeFile(fileContent);

        Assert.assertEquals(16, (long) results.getMetricCount(LocAnalyzer.JAVA_LOC_METRIC));
    }
}