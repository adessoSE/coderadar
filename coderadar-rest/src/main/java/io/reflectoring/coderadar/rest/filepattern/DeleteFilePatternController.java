package io.reflectoring.coderadar.rest.filepattern;

import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.delete.DeleteFilePatternFromProjectUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class DeleteFilePatternController {
  private final DeleteFilePatternFromProjectUseCase deleteFilePatternFromProjectUseCase;

  public DeleteFilePatternController(
      DeleteFilePatternFromProjectUseCase deleteFilePatternFromProjectUseCase) {
    this.deleteFilePatternFromProjectUseCase = deleteFilePatternFromProjectUseCase;
  }

  @DeleteMapping(path = "/projects/{projectId}/filePatterns/{filePatternId}")
  public ResponseEntity deleteFilePattern(
      @PathVariable(name = "filePatternId") Long filePatternId,  @PathVariable(name = "projectId") Long projectId) {
    deleteFilePatternFromProjectUseCase.delete(filePatternId, projectId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
