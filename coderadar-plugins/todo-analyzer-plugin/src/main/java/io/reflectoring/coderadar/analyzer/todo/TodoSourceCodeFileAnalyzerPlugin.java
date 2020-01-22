package io.reflectoring.coderadar.analyzer.todo;

import io.reflectoring.coderadar.plugin.api.*;
import java.io.IOException;
import java.util.List;

public class TodoSourceCodeFileAnalyzerPlugin implements SourceCodeFileAnalyzerPlugin {

  public static final Metric TODO_METRIC =
      new Metric("io.reflectoring.coderadar.analyzer.todo.TodoAnalyzer.todo");

  private String patternPropertyPrefix =
      TodoSourceCodeFileAnalyzerPlugin.class.getName() + ".pattern";

  private TodoFinder todoFinder = new TodoFinder(getDefaultPatterns());

  private String[] getDefaultPatterns() {
    return new String[] {"TODO", "FIXME", "todo", "fixme"};
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
      if (findings.size() == 0) {
        return metrics;
      }
      metrics.addFindings(TODO_METRIC, findings);
      metrics.setMetricCount(TODO_METRIC, (long) findings.size());
      return metrics;
    } catch (IOException e) {
      throw new AnalyzerException(e);
    }
  }
}
