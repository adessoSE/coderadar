package io.reflectoring.coderadar.rest.analyzing;

import io.reflectoring.coderadar.analyzer.port.driver.StartAnalyzingCommand;
import io.reflectoring.coderadar.analyzer.port.driver.StartAnalyzingUseCase;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class StartAnalyzingController {
  private final StartAnalyzingUseCase startAnalyzingUseCase;

  @Autowired
  public StartAnalyzingController(StartAnalyzingUseCase startAnalyzingUseCase) {
    this.startAnalyzingUseCase = startAnalyzingUseCase;
  }

  @PostMapping(path = "projects/{projectId}/analyze")
  public ResponseEntity startAnalyzing(
      @PathVariable("projectId") Long projectId,
      @Validated @RequestBody StartAnalyzingCommand command) {
    startAnalyzingUseCase.start(command, projectId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
