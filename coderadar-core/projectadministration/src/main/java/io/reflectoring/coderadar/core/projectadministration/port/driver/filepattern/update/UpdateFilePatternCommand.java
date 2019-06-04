package io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.update;

import io.reflectoring.coderadar.core.projectadministration.domain.InclusionType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFilePatternCommand {
  @NotBlank private String pattern;
  @NotNull private InclusionType inclusionType;
}
