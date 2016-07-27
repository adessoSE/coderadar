package org.wickedsource.coderadar.metric.rest.metricvalue;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryOutputParams {

    @NotNull
    @Size(min = 1)
    private List<String> metrics;

    public List<String> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<String> metrics) {
        this.metrics = metrics;
    }

    private void initMetrics() {
        if (metrics == null) {
            metrics = new ArrayList<>();
        }
    }

    public void addMetrics(String... metrics) {
        initMetrics();
        this.metrics.addAll(Arrays.asList(metrics));
    }

    public void addMetric(String metric) {
        initMetrics();
        this.metrics.add(metric);
    }
}
