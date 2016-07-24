package org.wickedsource.coderadar.metric.rest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Objects of this class provide parameters to query for metric values.
 */
public class QueryParams {

    @NotNull
    @Size(min = 1)
    private List<String> metricNames;

    @NotNull
    @Size(min = 1)
    private List<String> commitNames;

    public QueryParams() {
        metricNames = new ArrayList<>();
        commitNames = new ArrayList<>();
    }

    public void setMetricNames(List<String> metricNames) {
        this.metricNames = metricNames;
    }

    public void setCommitNames(List<String> commitNames) {
        this.commitNames = commitNames;
    }

    public void addMetricNames(String... metricNames) {
        this.metricNames.addAll(Arrays.asList(metricNames));
    }

    public void addCommitNames(String... commitNames) {
        this.commitNames.addAll(Arrays.asList(commitNames));
    }

    public List<String> getMetricNames() {
        return metricNames;
    }

    public List<String> getCommitNames() {
        return commitNames;
    }
}
