package io.reflectoring.coderadar.rest.project;

import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.port.driver.project.delete.DeleteProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class DeleteProjectController {
  private final DeleteProjectUseCase deleteProjectUseCase;

  @Autowired
  public DeleteProjectController(DeleteProjectUseCase deleteProjectUseCase) {
    this.deleteProjectUseCase = deleteProjectUseCase;
  }

  @DeleteMapping(produces = "application/json", path = "/projects/{projectId}")
  public ResponseEntity deleteProject(@PathVariable(name = "projectId") Long projectId) {
      deleteProjectUseCase.delete(projectId);
      return new ResponseEntity<>(HttpStatus.OK);
  }
}
