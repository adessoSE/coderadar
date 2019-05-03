package io.reflectoring.coderadar.rest.unit.module;

import io.reflectoring.coderadar.core.projectadministration.port.driver.module.create.CreateModuleCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.create.CreateModuleUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<Long> createModule(
      @RequestBody CreateModuleCommand command, @PathVariable(name = "projectId") Long projectId) {
    return new ResponseEntity<>(
        createModuleUseCase.createModule(command, projectId), HttpStatus.CREATED);
  }
}
