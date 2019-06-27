package io.reflectoring.coderadar.projectadministration.port.driver.module.create;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateModuleCommand {
  @NotBlank private String path;
}
