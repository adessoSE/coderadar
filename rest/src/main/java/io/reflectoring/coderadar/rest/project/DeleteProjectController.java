package io.reflectoring.coderadar.rest.project;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.reflectoring.coderadar.core.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.delete.DeleteProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteProjectController {
  private final DeleteProjectUseCase deleteProjectUseCase;

  @Autowired
  public DeleteProjectController(DeleteProjectUseCase deleteProjectUseCase) {
    this.deleteProjectUseCase = deleteProjectUseCase;
  }

  @DeleteMapping(produces = "application/json", path = "/projects/{projectId}")
  public ResponseEntity<String> deleteProject(@PathVariable(name = "projectId") Long projectId)
      throws JsonProcessingException {
    try {
      deleteProjectUseCase.delete(projectId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (ProjectNotFoundException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }
}
