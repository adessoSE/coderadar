package io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAnalyzerConfigurationCommand {
  @NotNull @NotEmpty private String analyzerName;
  @NotNull private Boolean enabled;
}
