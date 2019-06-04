package io.reflectoring.coderadar.core.projectadministration.port.driver.module.update;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateModuleCommand {
  @NotBlank private String path;
}
