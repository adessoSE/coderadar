package io.reflectoring.coderadar.rest.module;

import io.reflectoring.coderadar.core.projectadministration.port.driver.module.DeleteModuleUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/projects/{projectId}/modules")
public class DeleteModuleController {
  private final DeleteModuleUseCase deleteModuleUseCase;

  @Autowired
  public DeleteModuleController(DeleteModuleUseCase deleteModuleUseCase) {
    this.deleteModuleUseCase = deleteModuleUseCase;
  }

  @DeleteMapping("/{moduleId}")
  public ResponseEntity<String> deleteModule(@PathVariable Long moduleId) {
    deleteModuleUseCase.delete(moduleId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
