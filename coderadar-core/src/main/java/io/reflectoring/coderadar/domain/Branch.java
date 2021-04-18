package io.reflectoring.coderadar.domain;

import lombok.AllArgsConstructor;
import lombok.Value;

/** This class represents a branch in a project. */
@AllArgsConstructor
@Value
public class Branch {
  String name;
  long commitHash;
  boolean isTag;
}
