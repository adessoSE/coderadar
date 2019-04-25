package io.reflectoring.coderadar.rest.project;

import io.reflectoring.coderadar.core.projectadministration.port.driver.project.update.UpdateProjectCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.update.UpdateProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateProjectController {
  private final UpdateProjectUseCase updateProjectUseCase;

  @Autowired
  public UpdateProjectController(UpdateProjectUseCase updateProjectUseCase) {
    this.updateProjectUseCase = updateProjectUseCase;
  }

  @PostMapping(path = "/projects/{id}")
  public ResponseEntity<String> updateProject(
      @RequestBody UpdateProjectCommand command, @PathVariable Long projectId) {
    updateProjectUseCase.update(command, projectId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
