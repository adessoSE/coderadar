package io.reflectoring.coderadar.domain;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContributorsForFile {
  private String path;
  private Set<String> contributors;
}
