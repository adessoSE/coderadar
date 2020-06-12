package io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.create;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAnalyzerConfigurationCommand {
  @NotBlank private String analyzerName;
  private boolean enabled;
}
