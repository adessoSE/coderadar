package io.reflectoring.coderadar.contributor.domain;

import java.util.Set;
import lombok.Data;

@Data
public class Contributor {
  private long id;
  private String displayName;
  private Set<String> names;
  private Set<String> emailAddresses;
}
