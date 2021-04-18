package io.reflectoring.coderadar.rest.useradministration.teams;

import static io.reflectoring.coderadar.rest.GetProjectResponseMapper.mapProjects;

import io.reflectoring.coderadar.domain.Project;
import io.reflectoring.coderadar.domain.ProjectWithRoles;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.GetProjectResponseMapper;
import io.reflectoring.coderadar.rest.domain.GetProjectResponse;
import io.reflectoring.coderadar.rest.domain.ProjectWithRolesResponse;
import io.reflectoring.coderadar.useradministration.port.driver.teams.get.ListProjectsForTeamUseCase;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ListProjectsForTeamController implements AbstractBaseController {
  private final ListProjectsForTeamUseCase listProjectsForTeamUseCase;

  @GetMapping(path = "/teams/{teamId}/projects", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<GetProjectResponse>> listProjectsForTeam(@PathVariable long teamId) {
    List<Project> projects = listProjectsForTeamUseCase.listProjects(teamId);
    return new ResponseEntity<>(mapProjects(projects), HttpStatus.OK);
  }

  @GetMapping(
      path = "/teams/{teamId}/{userId}/projects",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<ProjectWithRolesResponse>> listProjectsForTeamWithUserRoles(
      @PathVariable long teamId, @PathVariable long userId) {
    List<ProjectWithRoles> projects =
        listProjectsForTeamUseCase.listProjectsWithUserRoles(teamId, userId);
    List<ProjectWithRolesResponse> responses = new ArrayList<>(projects.size());
    for (ProjectWithRoles p : projects) {
      responses.add(
          new ProjectWithRolesResponse(
              GetProjectResponseMapper.mapProject(p.getProject()), p.getRoles()));
    }
    return new ResponseEntity<>(responses, HttpStatus.OK);
  }
}
