package io.reflectoring.coderadar.rest.filepattern;

import io.reflectoring.coderadar.core.projectadministration.FilePatternNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.update.UpdateFilePatternCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.update.UpdateFilePatternUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateFilePatternController {
  private final UpdateFilePatternUseCase updateFilePatternForProjectUseCase;

  @Autowired
  public UpdateFilePatternController(UpdateFilePatternUseCase updateFilePatternForProjectUseCase) {
    this.updateFilePatternForProjectUseCase = updateFilePatternForProjectUseCase;
  }

  @PostMapping(path = "/projects/{projectId}/filePatterns/{filePatternId}")
  public ResponseEntity<String> updateFilePattern(
      @RequestBody @Validated UpdateFilePatternCommand command,
      @PathVariable(name = "filePatternId") Long filePatternId) {
    try {
      updateFilePatternForProjectUseCase.updateFilePattern(command, filePatternId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (FilePatternNotFoundException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }
}
