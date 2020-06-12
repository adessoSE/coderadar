package io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAnalyzerConfigurationCommand {
  @NotBlank private String analyzerName;
  private boolean enabled;
}
