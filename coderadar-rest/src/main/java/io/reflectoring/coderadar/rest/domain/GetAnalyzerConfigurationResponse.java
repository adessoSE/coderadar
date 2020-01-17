package io.reflectoring.coderadar.rest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAnalyzerConfigurationResponse {
  private Long id;
  private String analyzerName;
  private Boolean enabled;
}
