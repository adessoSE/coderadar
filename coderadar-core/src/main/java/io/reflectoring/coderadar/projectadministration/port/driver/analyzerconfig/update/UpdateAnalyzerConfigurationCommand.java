package io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.update;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAnalyzerConfigurationCommand {
  @NotBlank private String analyzerName;
  private boolean enabled;
}
