package io.reflectoring.coderadar.core.projectadministration.port.driver.module.get;

import lombok.Value;

@Value
public class GetModuleResponse {
  private Long id;
  private String path;
}
