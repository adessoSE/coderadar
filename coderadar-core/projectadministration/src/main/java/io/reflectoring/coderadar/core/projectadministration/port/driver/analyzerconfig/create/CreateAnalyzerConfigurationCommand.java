package io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.create;

import lombok.Value;

@Value
public class CreateAnalyzerConfigurationCommand {
  private String analyzerName;
  private Boolean enabled;
}
