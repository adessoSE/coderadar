package org.wickedsource.coderadar.metric.domain.metricvalue;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MetricValueDTO {

    @JsonIgnore
    private String commit;

    private String metric;

    private Long value;

    public MetricValueDTO() {

    }

    public MetricValueDTO(String commitName, String metricName, Long value) {
        this.commit = commitName;
        this.metric = metricName;
        this.value = value;
    }

    public String getMetric() {
        return metric;
    }

    public Long getValue() {
        return value;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
