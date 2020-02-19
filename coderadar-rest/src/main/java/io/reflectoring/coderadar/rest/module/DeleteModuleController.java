package io.reflectoring.coderadar.rest.module;

import io.reflectoring.coderadar.projectadministration.port.driver.module.delete.DeleteModuleUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class DeleteModuleController {
  private final DeleteModuleUseCase deleteModuleUseCase;

  public DeleteModuleController(DeleteModuleUseCase deleteModuleUseCase) {
    this.deleteModuleUseCase = deleteModuleUseCase;
  }

  @DeleteMapping(path = "/projects/{projectId}/modules/{moduleId}")
  public ResponseEntity<HttpStatus> deleteModule(
      @PathVariable(name = "moduleId") Long moduleId,
      @PathVariable(name = "projectId") Long projectId) {
    deleteModuleUseCase.delete(moduleId, projectId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
