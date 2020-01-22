package io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAnalyzerConfigurationCommand {
  @NotBlank private String analyzerName;
  @NotNull private Boolean enabled;
}
