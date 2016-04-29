package org.wickedsource.coderadar.analyzer.loc;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.api.FileMetrics;

import java.io.InputStream;

public class LocSourceCodeFileAnalyzerPluginTest {

    @Test
    public void metricIsCalculatedCorrectly() throws Exception {

        InputStream in = getClass().getResourceAsStream("AcceptedJavaFile.txt");
        byte[] fileContent = IOUtils.toByteArray(in);

        LocSourceCodeFileAnalyzerPlugin analyzer = new LocSourceCodeFileAnalyzerPlugin();
        FileMetrics results = analyzer.analyzeFile(fileContent);

        Assert.assertEquals(16, (long) results.getMetricCount(LocSourceCodeFileAnalyzerPlugin.JAVA_LOC_METRIC));
    }
}