package io.reflectoring.coderadar.rest.analyzing;

import io.reflectoring.coderadar.analyzer.port.driver.ResetAnalysisUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class ResetAnalysisController {
    private final ResetAnalysisUseCase resetAnalysisUseCase;

    public ResetAnalysisController(ResetAnalysisUseCase resetAnalysisUseCase) {
        this.resetAnalysisUseCase = resetAnalysisUseCase;
    }

    @PostMapping(path = "projects/{projectId}/analyze/reset")
    public ResponseEntity resetAnalysis(@PathVariable("projectId") Long projectId) {
        resetAnalysisUseCase.resetAnalysis(projectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
