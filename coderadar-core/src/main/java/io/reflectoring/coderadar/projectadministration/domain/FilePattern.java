package io.reflectoring.coderadar.projectadministration.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Contains an Ant-style path pattern to define a certain set of files that is of importance to a
 * coderadar Project.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilePattern {
  private long id;
  private String pattern;
  private InclusionType inclusionType;
}
