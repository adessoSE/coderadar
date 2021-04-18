package io.reflectoring.coderadar.rest.useradministration;

import io.reflectoring.coderadar.domain.ProjectWithRoles;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.GetProjectResponseMapper;
import io.reflectoring.coderadar.rest.domain.ProjectWithRolesResponse;
import io.reflectoring.coderadar.useradministration.port.driver.get.ListProjectsForUserUseCase;
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
public class ListProjectsForUserController implements AbstractBaseController {
  private final ListProjectsForUserUseCase listProjectsForUserUseCase;

  @GetMapping(path = "/users/{userId}/projects", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<ProjectWithRolesResponse>> listProjectsForUser(
      @PathVariable long userId) {
    List<ProjectWithRoles> projects = listProjectsForUserUseCase.listProjects(userId);
    List<ProjectWithRolesResponse> responses = new ArrayList<>(projects.size());
    for (ProjectWithRoles p : projects) {
      responses.add(
          new ProjectWithRolesResponse(
              GetProjectResponseMapper.mapProject(p.getProject()), p.getRoles()));
    }
    return new ResponseEntity<>(responses, HttpStatus.OK);
  }
}
