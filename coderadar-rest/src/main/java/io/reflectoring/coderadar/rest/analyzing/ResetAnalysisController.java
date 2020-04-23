package io.reflectoring.coderadar.rest.analyzing;

import io.reflectoring.coderadar.analyzer.port.driver.ResetAnalysisUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
public class ResetAnalysisController extends AbstractBaseController {
  private final ResetAnalysisUseCase resetAnalysisUseCase;

  public ResetAnalysisController(ResetAnalysisUseCase resetAnalysisUseCase) {
    this.resetAnalysisUseCase = resetAnalysisUseCase;
  }

  @PostMapping(path = "projects/{projectId}/analyze/reset")
  public ResponseEntity<HttpStatus> resetAnalysis(@PathVariable("projectId") long projectId) {
    resetAnalysisUseCase.resetAnalysis(projectId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
