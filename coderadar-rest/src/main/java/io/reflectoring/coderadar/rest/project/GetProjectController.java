package io.reflectoring.coderadar.rest.project;

import static io.reflectoring.coderadar.rest.GetProjectResponseMapper.mapProject;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.domain.ProjectWithRoles;
import io.reflectoring.coderadar.projectadministration.port.driver.project.get.GetProjectUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.GetProjectResponse;
import io.reflectoring.coderadar.rest.domain.ProjectWithRolesResponse;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
@RequiredArgsConstructor
public class GetProjectController implements AbstractBaseController {
  private final GetProjectUseCase getProjectUseCase;
  private final AuthenticationService authenticationService;

  @GetMapping(path = "/projects/{projectId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<GetProjectResponse> getProject(@PathVariable long projectId) {
    authenticationService.authenticateMember(projectId);
    Project project = getProjectUseCase.get(projectId);
    GetProjectResponse getProjectResponse = mapProject(project);
    return new ResponseEntity<>(getProjectResponse, HttpStatus.OK);
  }

  @GetMapping(
      path = "/users/{userId}/projects/{projectId}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ProjectWithRolesResponse> getProjectWithRoles(
      @PathVariable long projectId, @PathVariable long userId) {
    authenticationService.authenticateMember(projectId);
    ProjectWithRoles project = getProjectUseCase.getWithRoles(projectId, userId);
    ProjectWithRolesResponse response =
        new ProjectWithRolesResponse(mapProject(project.getProject()), project.getRoles());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
