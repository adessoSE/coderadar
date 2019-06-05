package io.reflectoring.coderadar.core.projectadministration.domain.analyzer;

public class GroupedByFileMetricValueDTO extends GroupedMetricValueDTO {

  public GroupedByFileMetricValueDTO(String metricName, Long value, String filepath) {
    super(metricName, value, filepath);
  }
}
