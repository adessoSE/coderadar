package io.reflectoring.coderadar.rest.filepattern;

import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.SetFilePatternForProjectCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.SetFilePatternForProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/projects/{projectId}/files")
public class SetFilePatternForProjectController {

  private final SetFilePatternForProjectUseCase setFilePatternForProjectUseCase;

  @Autowired
  public SetFilePatternForProjectController(
      SetFilePatternForProjectUseCase setFilePatternForProjectUseCase) {
    this.setFilePatternForProjectUseCase = setFilePatternForProjectUseCase;
  }

  @PostMapping
  public ResponseEntity<String> setFilePattern(
      @RequestBody SetFilePatternForProjectCommand command) {
    setFilePatternForProjectUseCase.setFilePattern(command);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
