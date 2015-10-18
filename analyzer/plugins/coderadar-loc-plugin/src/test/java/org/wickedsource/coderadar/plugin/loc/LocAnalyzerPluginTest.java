package org.wickedsource.coderadar.plugin.loc;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.plugin.api.FileMetrics;

import java.io.InputStream;

public class LocAnalyzerPluginTest {

    @Test
    public void testAnalyzeFile() throws Exception {

        InputStream in = getClass().getResourceAsStream("AcceptedFile.java");
        byte[] fileContent = IOUtils.toByteArray(in);

        LocAnalyzerPlugin analyzer = new LocAnalyzerPlugin();
        FileMetrics results = analyzer.analyzeFile(fileContent);

        Assert.assertEquals(16, (long) results.getMetricValue(LocAnalyzerPlugin.JAVA_LOC_METRIC));
    }
}