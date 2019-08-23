package io.reflectoring.coderadar.rest.module;

import io.reflectoring.coderadar.projectadministration.ModuleAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ModulePathInvalidException;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleUseCase;
import io.reflectoring.coderadar.rest.ErrorMessageResponse;
import io.reflectoring.coderadar.rest.IdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class CreateModuleController {
  private final CreateModuleUseCase createModuleUseCase;

  @Autowired
  public CreateModuleController(CreateModuleUseCase createModuleUseCase) {
    this.createModuleUseCase = createModuleUseCase;
  }

  @PostMapping(path = "/projects/{projectId}/modules")
  public ResponseEntity createModule(
      @RequestBody @Validated CreateModuleCommand command,
      @PathVariable(name = "projectId") Long projectId) throws ProjectIsBeingProcessedException {
    try {
      return new ResponseEntity<>(
          new IdResponse(createModuleUseCase.createModule(command, projectId)), HttpStatus.CREATED);
    } catch (ModulePathInvalidException e) {
      return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    } catch (ModuleAlreadyExistsException e) {
      return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.CONFLICT);
    }
  }
}
