package io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern;

import io.reflectoring.coderadar.core.projectadministration.domain.FileSetType;
import io.reflectoring.coderadar.core.projectadministration.domain.InclusionType;
import lombok.Value;

@Value
public class SetFilePatternForProjectCommand {
  private Long projectId;
  private String pattern;
  private InclusionType inclusionType;
  private FileSetType fileSetType;
}
