package io.reflectoring.coderadar.rest.unit.project;

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

  @DeleteMapping("/projects/{projectId}")
  public ResponseEntity<String> deleteProject(@PathVariable Long projectId) {
    deleteProjectUseCase.delete(projectId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
