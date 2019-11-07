package io.reflectoring.coderadar.rest.project;

import io.reflectoring.coderadar.projectadministration.ProjectAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.port.driver.project.update.UpdateProjectCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.update.UpdateProjectUseCase;
import io.reflectoring.coderadar.rest.ErrorMessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
@Transactional
public class UpdateProjectController {
  private final UpdateProjectUseCase updateProjectUseCase;

  @Autowired
  public UpdateProjectController(UpdateProjectUseCase updateProjectUseCase) {
    this.updateProjectUseCase = updateProjectUseCase;
  }

  @PostMapping(path = "/projects/{projectId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity updateProject(
      @RequestBody @Validated UpdateProjectCommand command,
      @PathVariable(name = "projectId") Long projectId)
          throws MalformedURLException {
    try {
      updateProjectUseCase.update(command, projectId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (ProjectAlreadyExistsException e) {
      return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }
}
