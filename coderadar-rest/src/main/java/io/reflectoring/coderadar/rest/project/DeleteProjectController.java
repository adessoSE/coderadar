package io.reflectoring.coderadar.rest.project;

import io.reflectoring.coderadar.projectadministration.port.driver.project.delete.DeleteProjectUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
public class DeleteProjectController extends AbstractBaseController {
  private final DeleteProjectUseCase deleteProjectUseCase;

  public DeleteProjectController(DeleteProjectUseCase deleteProjectUseCase) {
    this.deleteProjectUseCase = deleteProjectUseCase;
  }

  @DeleteMapping(path = "/projects/{projectId}")
  public ResponseEntity<HttpStatus> deleteProject(
      @PathVariable(name = "projectId") long projectId) {
    deleteProjectUseCase.delete(projectId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
