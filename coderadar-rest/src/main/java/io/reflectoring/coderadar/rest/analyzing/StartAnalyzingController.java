package io.reflectoring.coderadar.rest.analyzing;

import io.reflectoring.coderadar.analyzer.port.driver.StartAnalyzingUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class StartAnalyzingController {
  private final StartAnalyzingUseCase startAnalyzingUseCase;

  public StartAnalyzingController(StartAnalyzingUseCase startAnalyzingUseCase) {
    this.startAnalyzingUseCase = startAnalyzingUseCase;
  }

  @PostMapping(
      path = "projects/{projectId}/{branchName}/analyze",
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<HttpStatus> startAnalyzing(
      @PathVariable("projectId") Long projectId, @PathVariable("branchName") String branchName) {
    startAnalyzingUseCase.start(projectId, branchName);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
