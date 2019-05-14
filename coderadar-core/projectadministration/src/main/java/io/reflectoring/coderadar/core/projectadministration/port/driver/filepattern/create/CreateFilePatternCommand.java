package io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.create;

import io.reflectoring.coderadar.core.projectadministration.domain.InclusionType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Value;

@Value
public class CreateFilePatternCommand {
  @NotNull @NotEmpty private String pattern;
  @NotNull @NotEmpty private InclusionType inclusionType;
}
