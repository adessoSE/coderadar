package org.wickedsource.coderadar.analyzer.todo;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.wickedsource.coderadar.plugin.*;

public class TodoSourceCodeFileAnalyzerPlugin implements SourceCodeFileAnalyzerPlugin {

  public static final Metric TODO_METRIC =
      new Metric("org.wickedsource.coderadar.analyzer.todo.TodoAnalyzer.todo");

  private String patternPropertyPrefix =
      TodoSourceCodeFileAnalyzerPlugin.class.getName() + ".pattern";

  private TodoFinder todoFinder;

  private List<String> getDefaultPatterns() {
    return Arrays.asList("TODO", "FIXME", "todo", "fixme");
  }

  @Override
  public AnalyzerFileFilter getFilter() {
    return new DefaultFileFilter();
  }

  @Override
  public FileMetrics analyzeFile(String filename, byte[] fileContent) throws AnalyzerException {
    try {
      List<Finding> findings = todoFinder.findTodos(fileContent);
      FileMetrics metrics = new FileMetrics();
      metrics.addFindings(TODO_METRIC, findings);
      metrics.setMetricCount(TODO_METRIC, (long) findings.size());
      return metrics;
    } catch (IOException e) {
      throw new AnalyzerException(e);
    }
  }
}
