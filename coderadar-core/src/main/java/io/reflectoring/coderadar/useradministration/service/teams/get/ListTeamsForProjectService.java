package io.reflectoring.coderadar.useradministration.service.teams.get;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.useradministration.domain.Team;
import io.reflectoring.coderadar.useradministration.port.driven.ListTeamsForProjectPort;
import io.reflectoring.coderadar.useradministration.port.driver.teams.get.ListTeamsForProjectUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListTeamsForProjectService implements ListTeamsForProjectUseCase {

  private final ListTeamsForProjectPort listTeamsForProjectPort;
  private final GetProjectPort getProjectPort;

  @Override
  public List<Team> listTeamsForProject(long projectId) {
    if (getProjectPort.existsById(projectId)) {
      return listTeamsForProjectPort.listTeamsForProject(projectId);
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }
}
