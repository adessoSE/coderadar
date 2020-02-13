package io.reflectoring.coderadar.rest.domain;

import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFilePatternResponse {
  long id;
  String pattern;
  InclusionType inclusionType;
}
