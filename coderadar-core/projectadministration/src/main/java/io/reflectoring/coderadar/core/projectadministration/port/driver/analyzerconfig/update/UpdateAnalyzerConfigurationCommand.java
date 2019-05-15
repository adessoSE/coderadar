package io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Value;

@Value
public class UpdateAnalyzerConfigurationCommand {
  @NotNull @NotEmpty private String analyzerName;
  @NotNull @NotEmpty private Boolean enabled;
}
