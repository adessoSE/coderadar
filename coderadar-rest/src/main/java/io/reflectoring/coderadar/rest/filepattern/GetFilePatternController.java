package io.reflectoring.coderadar.rest.filepattern;

import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.get.GetFilePatternUseCase;
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
    return new ResponseEntity<>(getFilePatternUseCase.get(filePatternId), HttpStatus.OK);
  }
}
