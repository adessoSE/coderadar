package io.reflectoring.coderadar.query.domain;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContributorsForFile {
  private String path;
  private Set<String> contributors;
}
