package io.reflectoring.coderadar.core.analyzer.domain;

/**
 * Stores aggregated metric values over a certain group identified by a String.
 */
public abstract class GroupedMetricValueDTO extends MetricValueDTO {

    private String group;

    public GroupedMetricValueDTO() {
    }

    public GroupedMetricValueDTO(String metricName, Long value, String group) {
        super(metricName, value);
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
