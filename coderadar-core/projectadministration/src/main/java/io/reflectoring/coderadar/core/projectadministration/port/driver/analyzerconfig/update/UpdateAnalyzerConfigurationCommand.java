package io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAnalyzerConfigurationCommand {
  @NotBlank private String analyzerName;
  @NotNull private Boolean enabled;
}
