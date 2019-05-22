package io.reflectoring.coderadar.rest.module;

import io.reflectoring.coderadar.core.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.create.CreateModuleCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.create.CreateModuleUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateModuleController {
  private final CreateModuleUseCase createModuleUseCase;

  @Autowired
  public CreateModuleController(CreateModuleUseCase createModuleUseCase) {
    this.createModuleUseCase = createModuleUseCase;
  }

  @PostMapping(path = "/projects/{projectId}/modules")
  public ResponseEntity createModule(
      @RequestBody @Validated CreateModuleCommand command,
      @PathVariable(name = "projectId") Long projectId) {
    try {
      return new ResponseEntity<>(
          createModuleUseCase.createModule(command, projectId), HttpStatus.CREATED);
    } catch (ProjectNotFoundException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }
}
