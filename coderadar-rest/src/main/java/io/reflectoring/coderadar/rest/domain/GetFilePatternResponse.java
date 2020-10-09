package io.reflectoring.coderadar.rest.domain;

import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFilePatternResponse {
  private long id;
  private String pattern;
  private InclusionType inclusionType;
}
