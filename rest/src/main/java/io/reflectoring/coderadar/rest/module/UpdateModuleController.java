package io.reflectoring.coderadar.rest.module;

import io.reflectoring.coderadar.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driver.module.update.UpdateModuleCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.module.update.UpdateModuleUseCase;
import io.reflectoring.coderadar.rest.ErrorMessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateModuleController {
  private final UpdateModuleUseCase updateModuleUseCase;

  @Autowired
  public UpdateModuleController(UpdateModuleUseCase updateModuleUseCase) {
    this.updateModuleUseCase = updateModuleUseCase;
  }

  @PostMapping(path = "/projects/{projectId}/modules/{moduleId}")
  public ResponseEntity updateModule(
      @RequestBody @Validated UpdateModuleCommand command,
      @PathVariable(name = "moduleId") Long moduleId) {
    try {
      updateModuleUseCase.updateModule(command, moduleId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (ModuleNotFoundException e) {
      return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }
  }
}
