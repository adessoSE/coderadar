package io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAnalyzerConfigurationCommand {
  @NotBlank private String analyzerName;
  private boolean enabled;
}
