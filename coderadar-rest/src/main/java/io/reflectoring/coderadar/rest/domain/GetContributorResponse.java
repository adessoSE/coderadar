package io.reflectoring.coderadar.rest.domain;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetContributorResponse {
  private long id;
  private String displayName;
  private Set<String> names;
  private Set<String> emailAddresses;
}
