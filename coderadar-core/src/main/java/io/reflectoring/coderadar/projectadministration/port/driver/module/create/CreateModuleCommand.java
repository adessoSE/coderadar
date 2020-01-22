package io.reflectoring.coderadar.projectadministration.port.driver.module.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateModuleCommand {
  @NotBlank private String path;
}
