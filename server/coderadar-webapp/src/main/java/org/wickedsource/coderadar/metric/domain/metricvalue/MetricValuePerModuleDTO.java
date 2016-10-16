package org.wickedsource.coderadar.metric.domain.metricvalue;

public class MetricValuePerModuleDTO extends MetricValueDTO {

    private String module;

    public MetricValuePerModuleDTO() {

    }

    public MetricValuePerModuleDTO(String metricName, Long value, String module) {
        super(metricName, value);
        this.module = module;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
