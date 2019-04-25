package io.reflectoring.coderadar.rest.project;

import io.reflectoring.coderadar.core.projectadministration.port.driver.project.CreateProjectCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.CreateProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/projects")
public class CreateProjectController {
  private final CreateProjectUseCase createProjectUseCase;

  @Autowired
  public CreateProjectController(CreateProjectUseCase createProjectUseCase) {
    this.createProjectUseCase = createProjectUseCase;
  }

  @PostMapping
  public ResponseEntity<Long> createProject(@RequestBody CreateProjectCommand command) {
    return new ResponseEntity<>(createProjectUseCase.createProject(command), HttpStatus.CREATED);
  }
}
