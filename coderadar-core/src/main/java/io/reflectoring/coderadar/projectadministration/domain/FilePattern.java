package io.reflectoring.coderadar.projectadministration.domain;

import lombok.Data;

/**
 * Contains an Ant-style path pattern to define a certain set of files that is of importance to a
 * coderadar Project.
 */
@Data
public class FilePattern {
  private Long id;
  private String pattern;
  private InclusionType inclusionType;
}
