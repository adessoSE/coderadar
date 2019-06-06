package io.reflectoring.coderadar.analyzer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MetricValueDTO {

  private String metric;

  private Long value;
}
