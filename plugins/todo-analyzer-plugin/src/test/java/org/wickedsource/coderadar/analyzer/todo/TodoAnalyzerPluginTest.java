package org.wickedsource.coderadar.analyzer.todo;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.api.FileMetrics;
import org.wickedsource.coderadar.analyzer.api.Finding;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class TodoAnalyzerPluginTest {

    @Test
    public void metricIsCalculatedCorrectly() throws Exception {
        InputStream in = getClass().getResourceAsStream("acceptedFile.txt");
        byte[] fileContent = IOUtils.toByteArray(in);

        Properties patterns = new Properties();
        patterns.setProperty("org.wickedsource.coderadar.analyzer.todo.TodoAnalyzer.pattern1", "TODO");
        patterns.setProperty("org.wickedsource.coderadar.analyzer.todo.TodoAnalyzer.pattern2", "FIXME");

        TodoAnalyzerPlugin analyzer = new TodoAnalyzerPlugin();
        analyzer.configure(patterns);
        FileMetrics results = analyzer.analyzeFile(fileContent);

        List<Finding> findings = results.getFindings(TodoAnalyzerPlugin.TODO_METRIC);
        Assert.assertEquals(3, (long) results.getMetricCount(TodoAnalyzerPlugin.TODO_METRIC));
        Assert.assertEquals(3, findings.size());
        assertFinding(findings.get(0), 1, 1, 3, 6);
        assertFinding(findings.get(1), 3, 3, 1, 5);
        assertFinding(findings.get(2), 5, 5, 20, 24);
    }

    private void assertFinding(Finding finding, Integer lineStart, Integer lineEnd, Integer charStart, Integer charEnd){
        Assert.assertEquals(lineStart, finding.getLineStart());
        Assert.assertEquals(lineEnd, finding.getLineEnd());
        Assert.assertEquals(charStart, finding.getCharStart());
        Assert.assertEquals(charEnd, finding.getCharEnd());
    }
}
