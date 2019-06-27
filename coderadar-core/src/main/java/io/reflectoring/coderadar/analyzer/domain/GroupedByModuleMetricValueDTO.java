package io.reflectoring.coderadar.analyzer.domain;

public class GroupedByModuleMetricValueDTO extends GroupedMetricValueDTO {

  public GroupedByModuleMetricValueDTO(String metricName, Long value, String modulePath) {
    super(metricName, value, modulePath);
  }
}
