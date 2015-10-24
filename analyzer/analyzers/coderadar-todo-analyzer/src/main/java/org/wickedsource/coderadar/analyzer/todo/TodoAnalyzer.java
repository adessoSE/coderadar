package org.wickedsource.coderadar.analyzer.todo;

import org.wickedsource.coderadar.analyzer.api.*;

import java.io.IOException;
import java.util.*;

public class TodoAnalyzer implements Analyzer {

    public static final Metric TODO_METRIC = new Metric("org.wickedsource.coderadar.analyzer.todo.TodoAnalyzer.todo");

    private String patternPropertyPrefix = TodoAnalyzer.class.getName() + ".pattern";

    private TodoCounter todoCounter;

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
        todoCounter = new TodoCounter(todoPatterns);
    }

    private List<String> getDefaultPatterns() {
        return Arrays.asList("TODO", "FIXME", "todo", "fixme");
    }

    @Override
    public AnalyzerFilter getFilter() {
        return new DefaultFilter();
    }

    @Override
    public FileMetrics analyzeFile(byte[] fileContent) throws AnalyzerException {
        try {
            FileMetrics metrics = new FileMetrics();
            metrics.setMetricValue(TODO_METRIC, (long) todoCounter.count(fileContent));
            return metrics;
        } catch (IOException e) {
            throw new AnalyzerException(e);
        }
    }
}
