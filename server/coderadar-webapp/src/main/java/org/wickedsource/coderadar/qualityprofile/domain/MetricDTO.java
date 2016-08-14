package org.wickedsource.coderadar.qualityprofile.domain;

public class MetricDTO {

    private String metricName;

    private MetricType metricType;

    public MetricDTO() {
    }

    public MetricDTO(String metricName, MetricType metricType) {
        this.metricName = metricName;
        this.metricType = metricType;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public MetricType getMetricType() {
        return metricType;
    }

    public void setMetricType(MetricType metricType) {
        this.metricType = metricType;
    }

}

