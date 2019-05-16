package io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.create;

import io.reflectoring.coderadar.core.projectadministration.domain.InclusionType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFilePatternCommand {
  @NotNull @NotEmpty private String pattern;
  @NotNull private InclusionType inclusionType;
}
