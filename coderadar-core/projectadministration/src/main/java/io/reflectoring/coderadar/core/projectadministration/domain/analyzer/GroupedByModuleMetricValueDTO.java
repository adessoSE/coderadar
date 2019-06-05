package io.reflectoring.coderadar.core.projectadministration.domain.analyzer;

public class GroupedByModuleMetricValueDTO extends GroupedMetricValueDTO {

  public GroupedByModuleMetricValueDTO(String metricName, Long value, String modulePath) {
    super(metricName, value, modulePath);
  }
}
