package io.reflectoring.coderadar.core.projectadministration.port.driver.module.create;

import lombok.Value;

@Value
public class CreateModuleCommand {
  private String path;
}
