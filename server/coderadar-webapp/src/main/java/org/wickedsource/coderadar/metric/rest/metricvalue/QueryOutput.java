package org.wickedsource.coderadar.metric.rest.metricvalue;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryOutput {

    @Size(min = 1)
    private List<String> metrics;

    @Size(min = 1)
    private List<String> profiles;

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

    private void initProfiles() {
        if (profiles == null) {
            profiles = new ArrayList<>();
        }
    }

    public List<String> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<String> profiles) {
        this.profiles = profiles;
    }

    public void addProfile(String profileName) {
        initProfiles();
        this.profiles.add(profileName);
    }

    public void addProfiles(String... profileNames) {
        initProfiles();
        this.profiles.addAll(Arrays.asList(profileNames));
    }
}
