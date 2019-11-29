package io.reflectoring.coderadar.rest.analyzing;

import io.reflectoring.coderadar.analyzer.AnalyzingJobNotStartedException;
import io.reflectoring.coderadar.analyzer.port.driver.StopAnalyzingUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class StopAnalyzingController {
  private final StopAnalyzingUseCase stopAnalyzingUseCase;

  public StopAnalyzingController(StopAnalyzingUseCase stopAnalyzingUseCase) {
    this.stopAnalyzingUseCase = stopAnalyzingUseCase;
  }

  @GetMapping(path = "projects/{projectId}/stopAnalysis")
  public ResponseEntity<HttpStatus> stopAnalyzing(
          @PathVariable("projectId") Long projectId) {
    try {
      stopAnalyzingUseCase.stop(projectId);
    } catch (AnalyzingJobNotStartedException e){
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
