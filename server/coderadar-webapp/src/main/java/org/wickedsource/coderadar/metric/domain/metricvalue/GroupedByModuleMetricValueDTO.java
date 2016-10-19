package org.wickedsource.coderadar.metric.domain.metricvalue;

public class GroupedByModuleMetricValueDTO extends GroupedMetricValueDTO {

    public GroupedByModuleMetricValueDTO() {

    }

    public GroupedByModuleMetricValueDTO(String metricName, Long value, String modulePath) {
        super(metricName, value, modulePath);
    }

}
