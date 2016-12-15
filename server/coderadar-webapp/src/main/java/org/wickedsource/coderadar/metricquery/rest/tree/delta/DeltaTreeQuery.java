package org.wickedsource.coderadar.metricquery.rest.tree.delta;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Provides parameters to query for values of one or more metrics values at the time of two specific commits.
 */
public class DeltaTreeQuery {

    @NotNull
    private String commit1;

    @NotNull
    private String commit2;

    @Size(min = 1)
    private List<String> metrics;

    public DeltaTreeQuery() {
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

    public List<String> getMetrics() {
        return metrics;
    }

    public String getCommit1() {
        return commit1;
    }

    public void setCommit1(String commit1) {
        this.commit1 = commit1;
    }

    public String getCommit2() {
        return commit2;
    }

    public void setCommit2(String commit2) {
        this.commit2 = commit2;
    }
}

