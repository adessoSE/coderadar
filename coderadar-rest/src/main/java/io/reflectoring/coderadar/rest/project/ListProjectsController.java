package io.reflectoring.coderadar.rest.project;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driver.project.get.ListProjectsUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.GetProjectResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static io.reflectoring.coderadar.rest.GetProjectResponseMapper.mapProjects;

@Transactional
@RestController
public class ListProjectsController implements AbstractBaseController {
  private final ListProjectsUseCase listProjectsUseCase;

  public ListProjectsController(ListProjectsUseCase listProjectsUseCase) {
    this.listProjectsUseCase = listProjectsUseCase;
  }

  @GetMapping(path = "/projects", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<GetProjectResponse>> listProjects() {
    List<Project> projects = listProjectsUseCase.listProjects();
    return new ResponseEntity<>(mapProjects(projects), HttpStatus.OK);
  }
}
