package io.reflectoring.coderadar.rest.unit.filepattern;

import io.reflectoring.coderadar.core.projectadministration.FilePatternNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.delete.DeleteFilePatternFromProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteFilePatternController {
  private final DeleteFilePatternFromProjectUseCase deleteFilePatternFromProjectUseCase;

  @Autowired
  public DeleteFilePatternController(
      DeleteFilePatternFromProjectUseCase deleteFilePatternFromProjectUseCase) {
    this.deleteFilePatternFromProjectUseCase = deleteFilePatternFromProjectUseCase;
  }

  @DeleteMapping(path = "/projects/{projectId}/filePatterns/{filePatternId}")
  public ResponseEntity<String> deleteFilePattern(
      @PathVariable(name = "filePatternId") Long filePatternId) {
    try {
      deleteFilePatternFromProjectUseCase.delete(filePatternId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (FilePatternNotFoundException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }
}
