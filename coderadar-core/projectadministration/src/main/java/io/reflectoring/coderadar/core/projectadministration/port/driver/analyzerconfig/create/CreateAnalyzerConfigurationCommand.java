package io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.create;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAnalyzerConfigurationCommand {
  @NotNull @NotEmpty private String analyzerName;
  @NotNull private Boolean enabled;
}
