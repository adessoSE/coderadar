package io.reflectoring.coderadar.rest;

import io.reflectoring.coderadar.useradministration.domain.ProjectRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRoleJsonWrapper {
  private ProjectRole role;
}
