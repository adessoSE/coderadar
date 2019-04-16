package io.reflectoring.coderadar.core.projectadministration.port.driver.module;

import lombok.Value;

@Value
public class CreateModuleCommand {
  private Long projectId;
  private String path;
}
