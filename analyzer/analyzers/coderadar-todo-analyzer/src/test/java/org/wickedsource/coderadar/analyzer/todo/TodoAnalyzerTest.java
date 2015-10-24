package org.wickedsource.coderadar.analyzer.todo;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.api.FileMetrics;

import java.io.InputStream;
import java.util.Properties;

public class TodoAnalyzerTest {

    @Test
    public void metricIsCalculatedCorrectly() throws Exception {
        InputStream in = getClass().getResourceAsStream("acceptedFile.txt");
        byte[] fileContent = IOUtils.toByteArray(in);

        Properties patterns = new Properties();
        patterns.setProperty("org.wickedsource.coderadar.analyzer.todo.TodoAnalyzer.pattern1", "TODO");
        patterns.setProperty("org.wickedsource.coderadar.analyzer.todo.TodoAnalyzer.pattern2", "FIXME");

        TodoAnalyzer analyzer = new TodoAnalyzer();
        analyzer.configure(patterns);
        FileMetrics results = analyzer.analyzeFile(fileContent);

        Assert.assertEquals(3, (long) results.getMetricValue(TodoAnalyzer.TODO_METRIC));
    }
}