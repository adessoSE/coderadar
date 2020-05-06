package io.reflectoring.coderadar.useradministration.domain;

import java.util.Collections;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Team {
  private long id;
  private String name;
  private List<User> members = Collections.emptyList();
}
