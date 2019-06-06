package io.reflectoring.coderadar.projectadministration.port.driver.filepattern.get;

import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFilePatternResponse {
  Long id;
  String pattern;
  InclusionType inclusionType;
}
