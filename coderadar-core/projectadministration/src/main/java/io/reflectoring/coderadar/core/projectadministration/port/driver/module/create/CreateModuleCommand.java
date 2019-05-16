package io.reflectoring.coderadar.core.projectadministration.port.driver.module.create;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateModuleCommand {
  @NotNull @NotEmpty private String path;
}
