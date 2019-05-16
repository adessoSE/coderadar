package io.reflectoring.coderadar.rest.unit.filepattern;

import io.reflectoring.coderadar.core.projectadministration.FilePatternNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.get.GetFilePatternUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetFilePatternController {
  private final GetFilePatternUseCase getFilePatternUseCase;

  @Autowired
  public GetFilePatternController(GetFilePatternUseCase getFilePatternUseCase) {
    this.getFilePatternUseCase = getFilePatternUseCase;
  }

  @GetMapping(path = "/projects/{projectId}/filePatterns/{filePatternId}")
  public ResponseEntity getFilePattern(@PathVariable(name = "filePatternId") Long filePatternId) {
    try {
      return new ResponseEntity<>(getFilePatternUseCase.get(filePatternId), HttpStatus.OK);
    } catch (FilePatternNotFoundException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }
}
