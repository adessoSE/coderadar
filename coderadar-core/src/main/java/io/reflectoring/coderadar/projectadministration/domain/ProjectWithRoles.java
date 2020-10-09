package io.reflectoring.coderadar.projectadministration.domain;

import io.reflectoring.coderadar.useradministration.domain.ProjectRole;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectWithRoles {
  private Project project;
  private List<ProjectRole> roles;
}
