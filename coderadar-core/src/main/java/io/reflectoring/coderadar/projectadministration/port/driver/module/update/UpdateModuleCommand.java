package io.reflectoring.coderadar.projectadministration.port.driver.module.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateModuleCommand {
  @NotBlank private String path;
}
