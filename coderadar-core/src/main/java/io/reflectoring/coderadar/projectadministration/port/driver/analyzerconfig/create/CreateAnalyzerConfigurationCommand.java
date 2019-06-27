package io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAnalyzerConfigurationCommand {
  @NotBlank private String analyzerName;
  @NotNull private Boolean enabled;
}
