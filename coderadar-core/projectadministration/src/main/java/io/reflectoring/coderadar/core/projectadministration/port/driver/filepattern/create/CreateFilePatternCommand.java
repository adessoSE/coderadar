package io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.create;

import io.reflectoring.coderadar.core.projectadministration.domain.InclusionType;
import lombok.Value;

@Value
public class CreateFilePatternCommand {
  private String pattern;
  private InclusionType inclusionType;
}
