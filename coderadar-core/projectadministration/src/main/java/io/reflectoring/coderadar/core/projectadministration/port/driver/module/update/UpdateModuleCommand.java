package io.reflectoring.coderadar.core.projectadministration.port.driver.module.update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Value;

@Value
public class UpdateModuleCommand {
  @NotNull @NotEmpty private String path;
}
