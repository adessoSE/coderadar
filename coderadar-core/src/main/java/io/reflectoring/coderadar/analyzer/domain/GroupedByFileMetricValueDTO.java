package io.reflectoring.coderadar.analyzer.domain;

public class GroupedByFileMetricValueDTO extends GroupedMetricValueDTO {

  public GroupedByFileMetricValueDTO(String metricName, Long value, String filepath) {
    super(metricName, value, filepath);
  }
}
