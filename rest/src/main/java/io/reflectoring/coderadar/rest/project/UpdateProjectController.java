package io.reflectoring.coderadar.rest.project;

import io.reflectoring.coderadar.core.projectadministration.port.driver.project.UpdateProjectCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.UpdateProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/projects")
public class UpdateProjectController {
  private final UpdateProjectUseCase updateProjectUseCase;

  @Autowired
  public UpdateProjectController(UpdateProjectUseCase updateProjectUseCase) {
    this.updateProjectUseCase = updateProjectUseCase;
  }

  @PostMapping
  public ResponseEntity<String> updateProject(@RequestBody UpdateProjectCommand command) {
    updateProjectUseCase.update(command);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
