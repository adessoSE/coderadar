package io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.update;

import io.reflectoring.coderadar.core.projectadministration.domain.InclusionType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFilePatternCommand {
  @NotNull @NotEmpty private String pattern;
  @NotNull private InclusionType inclusionType;
}
