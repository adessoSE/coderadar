package io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.update;

import io.reflectoring.coderadar.core.projectadministration.domain.InclusionType;
import lombok.Value;

@Value
public class UpdateFilePatternCommand {
  String pattern;
  InclusionType inclusionType;
}
