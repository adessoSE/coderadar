package io.reflectoring.coderadar.rest.project;

import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.IdResponse;
import java.net.MalformedURLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
public class CreateProjectController extends AbstractBaseController {
  private final CreateProjectUseCase createProjectUseCase;

  public CreateProjectController(CreateProjectUseCase createProjectUseCase) {
    this.createProjectUseCase = createProjectUseCase;
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE,
      path = "/projects")
  public ResponseEntity<IdResponse> createProject(
      @RequestBody @Validated CreateProjectCommand command) throws MalformedURLException {
    return new ResponseEntity<>(
        new IdResponse(createProjectUseCase.createProject(command)), HttpStatus.CREATED);
  }
}
