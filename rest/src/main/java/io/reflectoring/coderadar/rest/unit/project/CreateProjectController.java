package io.reflectoring.coderadar.rest.unit.project;

import io.reflectoring.coderadar.core.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.create.CreateProjectUseCase;
import java.net.MalformedURLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional
public class CreateProjectController {
  private final CreateProjectUseCase createProjectUseCase;

  @Autowired
  public CreateProjectController(CreateProjectUseCase createProjectUseCase) {
    this.createProjectUseCase = createProjectUseCase;
  }

  @PostMapping(produces = "application/json", path = "/projects")
  public ResponseEntity<Long> createProject(@RequestBody @Validated CreateProjectCommand command)
      throws MalformedURLException {
    return new ResponseEntity<>(createProjectUseCase.createProject(command), HttpStatus.CREATED);
  }
}
