package org.wickedsource.coderadar.metric.domain.metricvalue;

public class GroupedByFileMetricValueDTO extends GroupedMetricValueDTO {

  public GroupedByFileMetricValueDTO() {}

  public GroupedByFileMetricValueDTO(String metricName, Long value, String filepath) {
    super(metricName, value, filepath);
  }
}
