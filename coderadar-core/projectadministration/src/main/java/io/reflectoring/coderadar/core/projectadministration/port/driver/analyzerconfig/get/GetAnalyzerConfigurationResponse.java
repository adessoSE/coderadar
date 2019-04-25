package io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.get;

import lombok.Value;

@Value
public class GetAnalyzerConfigurationResponse {
  private Long id;
  private String analyzerName;
  private Boolean enabled;
}
