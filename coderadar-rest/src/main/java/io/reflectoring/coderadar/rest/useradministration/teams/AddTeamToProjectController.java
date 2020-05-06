package io.reflectoring.coderadar.rest.useradministration.teams;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.ProjectRoleJsonWrapper;
import io.reflectoring.coderadar.useradministration.port.driver.teams.AddTeamToProjectUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class AddTeamToProjectController implements AbstractBaseController {
  private final AddTeamToProjectUseCase addTeamToProjectUseCase;

  public AddTeamToProjectController(AddTeamToProjectUseCase addTeamToProjectUseCase) {
    this.addTeamToProjectUseCase = addTeamToProjectUseCase;
  }

  @PostMapping(
      path = "/projects/{projectId}/teams/{teamId}",
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<HttpStatus> addTeamToProject(
      @PathVariable long projectId,
      @PathVariable long teamId,
      @RequestBody ProjectRoleJsonWrapper role) {
    addTeamToProjectUseCase.addTeamToProject(projectId, teamId, role.getRole());
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
