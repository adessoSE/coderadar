package io.reflectoring.coderadar.rest.filepattern;

import io.reflectoring.coderadar.projectadministration.FilePatternNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.delete.DeleteFilePatternFromProjectUseCase;
import io.reflectoring.coderadar.rest.ErrorMessageResponse;
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
  public ResponseEntity deleteFilePattern(
      @PathVariable(name = "filePatternId") Long filePatternId) {
    try {
      deleteFilePatternFromProjectUseCase.delete(filePatternId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (FilePatternNotFoundException e) {
      return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }
  }
}
