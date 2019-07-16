package io.reflectoring.coderadar.rest.module;

import io.reflectoring.coderadar.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.port.driver.module.delete.DeleteModuleUseCase;
import io.reflectoring.coderadar.rest.ErrorMessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteModuleController {
  private final DeleteModuleUseCase deleteModuleUseCase;

  @Autowired
  public DeleteModuleController(DeleteModuleUseCase deleteModuleUseCase) {
    this.deleteModuleUseCase = deleteModuleUseCase;
  }

  @DeleteMapping(
    path = "/projects/{projectId}/modules/{moduleId}"
  )
  public ResponseEntity deleteModule(@PathVariable(name = "moduleId") Long moduleId, @PathVariable(name = "projectId") Long projectId) {
    try {
      deleteModuleUseCase.delete(moduleId, projectId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (ModuleNotFoundException e) {
      return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (ProjectIsBeingProcessedException e) {
      return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }
}
