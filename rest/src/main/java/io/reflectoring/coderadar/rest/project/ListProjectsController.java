package io.reflectoring.coderadar.rest.project;

import io.reflectoring.coderadar.projectadministration.port.driver.project.get.GetProjectResponse;
import io.reflectoring.coderadar.projectadministration.port.driver.project.get.ListProjectsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ListProjectsController {
  private final ListProjectsUseCase listProjectsUseCase;

  @Autowired
  public ListProjectsController(ListProjectsUseCase listProjectsUseCase) {
    this.listProjectsUseCase = listProjectsUseCase;
  }

  @GetMapping(path = "/projects")
  public ResponseEntity<List<GetProjectResponse>> listProjects() {
    return new ResponseEntity<>(listProjectsUseCase.listProjects(), HttpStatus.OK);
  }
}
