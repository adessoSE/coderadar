package io.reflectoring.coderadar.projectadministration.port.driver.filepattern.update;

import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFilePatternCommand {
  @NotBlank private String pattern;
  @NotNull private InclusionType inclusionType;
}
