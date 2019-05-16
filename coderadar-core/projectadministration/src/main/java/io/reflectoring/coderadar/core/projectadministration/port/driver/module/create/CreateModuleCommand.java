package io.reflectoring.coderadar.core.projectadministration.port.driver.module.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateModuleCommand {
  @NotNull @NotEmpty private String path;
}
