package io.reflectoring.coderadar.core.projectadministration.port.driver.module;

import lombok.Value;

@Value
public class UpdateModuleCommand {
  private Long id;
  private String path;
}
