package io.reflectoring.coderadar.core.projectadministration.port.driver.module.create;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Value;

@Value
public class CreateModuleCommand {
  @NotNull @NotEmpty private String path;
}
