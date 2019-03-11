package org.wickedsource.coderadar.qualityprofile.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MetricDTO {

  private String metricName;

  private MetricType metricType;
}
