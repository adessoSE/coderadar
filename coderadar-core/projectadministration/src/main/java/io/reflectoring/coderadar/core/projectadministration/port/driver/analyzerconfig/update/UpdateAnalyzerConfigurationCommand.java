package io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.update;

import lombok.Value;

@Value
public class UpdateAnalyzerConfigurationCommand {
  String analyzerName;
  Boolean enabled;
}
