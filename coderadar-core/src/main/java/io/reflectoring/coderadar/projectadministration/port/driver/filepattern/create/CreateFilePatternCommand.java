package io.reflectoring.coderadar.projectadministration.port.driver.filepattern.create;

import io.reflectoring.coderadar.domain.InclusionType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFilePatternCommand {
  @NotBlank private String pattern;
  @NotNull private InclusionType inclusionType;
}
