package io.reflectoring.coderadar.rest.filepattern;

import io.reflectoring.coderadar.core.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.create.CreateFilePatternCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.create.CreateFilePatternUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateFilePatternController {
  private final CreateFilePatternUseCase createFilePatternUseCase;

  @Autowired
  public CreateFilePatternController(CreateFilePatternUseCase createFilePatternUseCase) {
    this.createFilePatternUseCase = createFilePatternUseCase;
  }

  @PostMapping(path = "/projects/{projectId}/filePatterns")
  public ResponseEntity createFilePattern(
      @RequestBody @Validated CreateFilePatternCommand command,
      @PathVariable(name = "projectId") Long projectId) {
    try {
      return new ResponseEntity<>(
          createFilePatternUseCase.createFilePattern(command, projectId), HttpStatus.CREATED);
    } catch (ProjectNotFoundException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }
}
