package io.reflectoring.coderadar.contributor.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class Contributor {
  private Long id;
  private String displayName;
  private Set<String> names;
  private Set<String> emailAddresses;
}
