package org.wickedsource.coderadar.metric.rest.metricvalue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.ResourceSupport;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class MetricOutputsResources extends ResourceSupport {

    private Map<String, List<MetricValueDTO>> commits;

    public MetricOutputsResources() {
        this.commits = new HashMap();
    }

    @JsonIgnore
    public List<MetricValueDTO> getMetricsForCommit(String commit) {
        return commits.get(commit);
    }

    @JsonIgnore
    public Long getValueForCommitAndMetric(String commit, String metric) {
        List<MetricValueDTO> metricValues = commits.get(commit);
        if (metricValues == null) {
            return null;
        }
        for (MetricValueDTO metricValue : metricValues) {
            if (metric.equals(metricValue.getMetric())) {
                return metricValue.getValue();
            }
        }
        return null;
    }

    public Map<String, List<MetricValueDTO>> getCommits() {
        return this.commits;
    }

    public void setCommits(Map<String, List<MetricValueDTO>> commits) {
        this.commits = commits;
    }

    /**
     * Takes a flat list of metric values and aggregates them by commit.
     */
    public void setCommitsList(List<MetricValueDTO> metricValues) {
        for (MetricValueDTO metricValue : metricValues) {
            List<MetricValueDTO> metricsList = commits.get(metricValue.getCommit());
            if (metricsList == null) {
                metricsList = new ArrayList<>();
                commits.put(metricValue.getCommit(), metricsList);
            }
            metricsList.add(metricValue);
        }
    }

}
