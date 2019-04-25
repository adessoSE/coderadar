package io.reflectoring.coderadar.rest.module;

import io.reflectoring.coderadar.core.projectadministration.port.driver.module.CreateModuleCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.CreateModuleUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/projects/{projectId}/modules")
public class CreateModuleController {
  private final CreateModuleUseCase createModuleUseCase;

  @Autowired
  public CreateModuleController(CreateModuleUseCase createModuleUseCase) {
    this.createModuleUseCase = createModuleUseCase;
  }

  @PostMapping
  public ResponseEntity<Long> createModule(@RequestBody CreateModuleCommand command) {
    return new ResponseEntity<>(createModuleUseCase.createModule(command), HttpStatus.CREATED);
  }
}
