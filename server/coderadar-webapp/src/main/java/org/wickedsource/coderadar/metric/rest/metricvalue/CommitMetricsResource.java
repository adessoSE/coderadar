package org.wickedsource.coderadar.metric.rest.metricvalue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.ResourceSupport;
import org.wickedsource.coderadar.metric.domain.MetricValueDTO;

import java.util.*;

@SuppressWarnings("unchecked")
public class CommitMetricsResource extends ResourceSupport {

    private Map<String, List<MetricValueDTO>> commitMetrics;

    public CommitMetricsResource() {
        this.commitMetrics = new HashMap();
    }

    public CommitMetricsResource(Collection<MetricValueDTO> metricValues) {
        this.commitMetrics = new HashMap();
        for (MetricValueDTO metricValue : metricValues) {
            List<MetricValueDTO> metricsList = commitMetrics.get(metricValue.getCommit());
            if (metricsList == null) {
                metricsList = new ArrayList<>();
                commitMetrics.put(metricValue.getCommit(), metricsList);
            }
            metricsList.add(metricValue);
        }
    }

    @JsonIgnore
    public List<MetricValueDTO> getMetricsForCommit(String commit) {
        return commitMetrics.get(commit);
    }

    @JsonIgnore
    public Long getValueForCommitAndMetric(String commit, String metric) {
        List<MetricValueDTO> metricValues = commitMetrics.get(commit);
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

    @JsonIgnore
    public Set<String> getCommits() {
        return commitMetrics.keySet();
    }

    public Map<String, List<MetricValueDTO>> getCommitMetrics() {
        return this.commitMetrics;
    }

    public void setCommitMetrics(Map<String, List<MetricValueDTO>> commitMetrics) {
        this.commitMetrics = commitMetrics;
    }

}
