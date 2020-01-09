package io.reflectoring.coderadar.analyzer.todo;

import io.reflectoring.coderadar.plugin.api.FileMetrics;
import io.reflectoring.coderadar.plugin.api.Metric;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TodoAnalyzerPluginTest {
  @Test
  public void returnsMetricsSuccessfully() throws IOException {
    InputStream in = getClass().getResourceAsStream("acceptedFile.txt");
    byte[] fileContent = IOUtils.toByteArray(in);

    Metric todoMetric = new Metric("io.reflectoring.coderadar.analyzer.todo.TodoAnalyzer.todo");

    TodoSourceCodeFileAnalyzerPlugin plugin = new TodoSourceCodeFileAnalyzerPlugin();
    FileMetrics results = plugin.analyzeFile("acceptedFile.txt", fileContent);

    Assertions.assertTrue(results.getMetrics().contains(todoMetric));
    Assertions.assertEquals(3, results.getFindings(todoMetric).size());
    Assertions.assertEquals(3L, results.getMetricCount(todoMetric).longValue());
  }
}
