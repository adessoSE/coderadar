package io.reflectoring.coderadar.projectadministration.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/** This class represents a branch in a project. */
@Data
@NoArgsConstructor
public class Branch {
  private long id;
  private String name;
  private String commitHash;
}
