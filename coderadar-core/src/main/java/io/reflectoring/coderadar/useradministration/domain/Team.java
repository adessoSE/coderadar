package io.reflectoring.coderadar.useradministration.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
public class Team {
  private long id;
  private String name;
  private List<User> members = Collections.emptyList();
}
