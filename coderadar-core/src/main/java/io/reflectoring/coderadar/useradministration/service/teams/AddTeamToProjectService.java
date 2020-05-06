package io.reflectoring.coderadar.useradministration.service.teams;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.useradministration.domain.ProjectRole;
import io.reflectoring.coderadar.useradministration.port.driven.AddTeamToProjectPort;
import io.reflectoring.coderadar.useradministration.port.driver.teams.AddTeamToProjectUseCase;
import org.springframework.stereotype.Service;

@Service
public class AddTeamToProjectService implements AddTeamToProjectUseCase {

  private final GetProjectPort getProjectPort;
  private final AddTeamToProjectPort addTeamToProjectPort;

  public AddTeamToProjectService(
      GetProjectPort getProjectPort, AddTeamToProjectPort addTeamToProjectPort) {
    this.getProjectPort = getProjectPort;
    this.addTeamToProjectPort = addTeamToProjectPort;
  }

  @Override
  public void addTeamToProject(long projectId, long teamId, ProjectRole role) {
    if (!getProjectPort.existsById(projectId)) {
      throw new ProjectNotFoundException(projectId);
    }
    addTeamToProjectPort.addTeamToProject(projectId, teamId, role);
  }
}
