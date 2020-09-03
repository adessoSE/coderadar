package io.reflectoring.coderadar.rest.useradministration;

import static io.reflectoring.coderadar.rest.GetProjectResponseMapper.mapProjects;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.GetProjectResponse;
import io.reflectoring.coderadar.useradministration.port.driver.get.ListProjectsForUserUseCase;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class ListProjectsForUserController implements AbstractBaseController {
  private final ListProjectsForUserUseCase listProjectsForUserUseCase;

  public ListProjectsForUserController(ListProjectsForUserUseCase listProjectsForUserUseCase) {
    this.listProjectsForUserUseCase = listProjectsForUserUseCase;
  }

  @GetMapping(path = "/users/{userId}/projects", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<GetProjectResponse>> listProjectsForUser(@PathVariable long userId) {
    List<Project> projects = listProjectsForUserUseCase.listProjects(userId);
    return new ResponseEntity<>(mapProjects(projects), HttpStatus.OK);
  }
}
