package io.reflectoring.coderadar.projectadministration.port.driver.filepattern.create;

import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFilePatternCommand {
  @NotBlank private String pattern;
  @NotNull private InclusionType inclusionType;
}
