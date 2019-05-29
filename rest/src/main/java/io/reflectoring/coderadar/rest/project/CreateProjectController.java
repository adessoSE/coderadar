package io.reflectoring.coderadar.rest.project;

import io.reflectoring.coderadar.core.projectadministration.ProjectStillExistsException;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.create.CreateProjectUseCase;
import io.reflectoring.coderadar.rest.ErrorMessageResponse;
import io.reflectoring.coderadar.rest.IdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
@Transactional
public class CreateProjectController {
  private final CreateProjectUseCase createProjectUseCase;

  @Autowired
  public CreateProjectController(CreateProjectUseCase createProjectUseCase) {
    this.createProjectUseCase = createProjectUseCase;
  }

  @PostMapping(produces = "application/json", path = "/projects")
  public ResponseEntity createProject(@RequestBody @Validated CreateProjectCommand command)
      throws MalformedURLException {
    try {
      return new ResponseEntity<>(
          new IdResponse(createProjectUseCase.createProject(command)), HttpStatus.CREATED);
    } catch (ProjectStillExistsException e) {
      return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }
}
