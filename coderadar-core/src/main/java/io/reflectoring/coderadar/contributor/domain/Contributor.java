package io.reflectoring.coderadar.contributor.domain;

import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Contributor {
  private Long id;
  private String displayName;
  private Set<String> names;
  private Set<String> emailAddresses;
}
