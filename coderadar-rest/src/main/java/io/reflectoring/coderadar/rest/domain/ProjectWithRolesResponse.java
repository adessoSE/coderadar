package io.reflectoring.coderadar.rest.domain;

import io.reflectoring.coderadar.domain.ProjectRole;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectWithRolesResponse {
  private GetProjectResponse project;
  private List<ProjectRole> roles;
}
