package io.reflectoring.coderadar.rest.filepattern;

import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.update.UpdateFilePatternCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.update.UpdateFilePatternUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class UpdateFilePatternController {
  private final UpdateFilePatternUseCase updateFilePatternForProjectUseCase;

  @Autowired
  public UpdateFilePatternController(UpdateFilePatternUseCase updateFilePatternForProjectUseCase) {
    this.updateFilePatternForProjectUseCase = updateFilePatternForProjectUseCase;
  }

  @PostMapping(path = "/projects/{projectId}/filePatterns/{filePatternId}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity updateFilePattern(
      @RequestBody @Validated UpdateFilePatternCommand command,
      @PathVariable(name = "filePatternId") Long filePatternId) {
    updateFilePatternForProjectUseCase.updateFilePattern(command, filePatternId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
