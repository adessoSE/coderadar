package io.reflectoring.coderadar.projectadministration.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** This class represents a branch in a project. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Branch {
  private String name;
  private String commitHash;
}
