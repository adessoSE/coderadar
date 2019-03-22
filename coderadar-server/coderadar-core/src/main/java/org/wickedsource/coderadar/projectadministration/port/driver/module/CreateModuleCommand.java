package org.wickedsource.coderadar.projectadministration.port.driver.module;

import lombok.Value;

@Value
public class CreateModuleCommand {
  private Long id;
  private String path;
}
