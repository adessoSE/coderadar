package io.reflectoring.coderadar.analyzer.todo;

import io.reflectoring.coderadar.plugin.api.FileMetrics;
import io.reflectoring.coderadar.plugin.api.Finding;
import io.reflectoring.coderadar.plugin.api.Metric;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TodoAnalyzerPluginTest {
  private final Metric todoMetric =
      new Metric("io.reflectoring.coderadar.analyzer.todo.TodoAnalyzer.todo");
  private final TodoSourceCodeFileAnalyzerPlugin plugin = new TodoSourceCodeFileAnalyzerPlugin();
  private FileMetrics results;

  @BeforeEach
  public void setup() throws IOException {
    InputStream in = getClass().getResourceAsStream("acceptedFile.txt");
    byte[] fileContent = IOUtils.toByteArray(in);

    results = plugin.analyzeFile("acceptedFile.txt", fileContent);
  }

  @Test
  public void returnsCorrectNumberOfMetrics() {
    Assertions.assertTrue(results.getMetrics().contains(todoMetric));
    Assertions.assertEquals(3, results.getFindings(todoMetric).size());
    Assertions.assertEquals(3L, results.getMetricCount(todoMetric).longValue());
  }

  @Test
  public void returnsCorrectFindings() {
    Finding firstFinding = new Finding(1, 1, 3, 6);
    Finding secondFinding = new Finding(3, 3, 1, 5);
    Finding thirdFinding = new Finding(5, 5, 20, 24);
    Assertions.assertTrue(results.getFindings(todoMetric).contains(firstFinding));
    Assertions.assertTrue(results.getFindings(todoMetric).contains(secondFinding));
    Assertions.assertTrue(results.getFindings(todoMetric).contains(thirdFinding));
  }
}
