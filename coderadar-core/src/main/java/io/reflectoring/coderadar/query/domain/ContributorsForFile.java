package io.reflectoring.coderadar.query.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContributorsForFile {
  private String path;
  private Set<String> contributors;
}
