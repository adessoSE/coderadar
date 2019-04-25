package io.reflectoring.coderadar.rest.module;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.UpdateModuleCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.UpdateModuleUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/projects/{projectId}/modules")
public class UpdateModuleController {
  private final UpdateModuleUseCase updateModuleUseCase;

  @Autowired
  public UpdateModuleController(UpdateModuleUseCase updateModuleUseCase) {
    this.updateModuleUseCase = updateModuleUseCase;
  }

  @PostMapping("/{moduleId}")
  public ResponseEntity<Module> updateModule(@RequestBody UpdateModuleCommand command) {
    return new ResponseEntity<>(updateModuleUseCase.updateModule(command), HttpStatus.OK);
  }
}
