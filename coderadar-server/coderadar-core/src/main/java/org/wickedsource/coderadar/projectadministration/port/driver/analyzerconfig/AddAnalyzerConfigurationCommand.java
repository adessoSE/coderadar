package org.wickedsource.coderadar.projectadministration.port.driver.analyzerconfig;

import lombok.Value;

@Value
public class AddAnalyzerConfigurationCommand {
  private Long projectId;
  private String analyzerName;
  private Boolean enabled;
}
