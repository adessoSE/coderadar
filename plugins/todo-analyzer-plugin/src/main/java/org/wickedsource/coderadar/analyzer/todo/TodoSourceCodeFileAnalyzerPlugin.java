package org.wickedsource.coderadar.analyzer.todo;

import org.wickedsource.coderadar.analyzer.api.*;

import java.io.IOException;
import java.util.*;

public class TodoSourceCodeFileAnalyzerPlugin implements SourceCodeFileAnalyzerPlugin {

    public static final Metric TODO_METRIC = new Metric("org.wickedsource.coderadar.analyzer.todo.TodoAnalyzer.todo");

    private String patternPropertyPrefix = TodoSourceCodeFileAnalyzerPlugin.class.getName() + ".pattern";

    private TodoFinder todoFinder;

    @Override
    public void configure(Properties properties) {
        List<String> todoPatterns = new ArrayList<>();
        for (Map.Entry entry : properties.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (key.startsWith(patternPropertyPrefix)) {
                todoPatterns.add(value);
            }
        }
        if (todoPatterns.isEmpty()) {
            todoPatterns = getDefaultPatterns();
        }
        todoFinder = new TodoFinder(todoPatterns.toArray(new String[todoPatterns.size()]));
    }

    private List<String> getDefaultPatterns() {
        return Arrays.asList("TODO", "FIXME", "todo", "fixme");
    }

    @Override
    public AnalyzerFileFilter getFilter() {
        return new DefaultFileFilter();
    }

    @Override
    public FileMetrics analyzeFile(byte[] fileContent) throws AnalyzerException {
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

    @Override
    public void destroy() {
        // nothing to destroy
    }
}
