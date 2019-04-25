package io.reflectoring.coderadar.rest.project;

import io.reflectoring.coderadar.core.projectadministration.port.driver.project.DeleteProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/projects")
public class DeleteProjectController {
  private final DeleteProjectUseCase deleteProjectUseCase;

  @Autowired
  public DeleteProjectController(DeleteProjectUseCase deleteProjectUseCase) {
    this.deleteProjectUseCase = deleteProjectUseCase;
  }

  @DeleteMapping("/{projectId}")
  public ResponseEntity<String> deleteProject(@PathVariable Long projectId) {
    deleteProjectUseCase.delete(projectId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
