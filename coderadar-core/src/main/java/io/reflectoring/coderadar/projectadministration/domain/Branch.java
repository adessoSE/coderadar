package io.reflectoring.coderadar.projectadministration.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Branch {
  private long id;
  private String name;
  private String commitHash;
}
