package io.reflectoring.coderadar.rest.analyzing;

import io.reflectoring.coderadar.analyzer.port.driver.ResetAnalysisUseCase;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.rest.ErrorMessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResetAnalysisController {
    private final ResetAnalysisUseCase resetAnalysisUseCase;

    @Autowired
    public ResetAnalysisController(ResetAnalysisUseCase resetAnalysisUseCase) {
        this.resetAnalysisUseCase = resetAnalysisUseCase;
    }

    @PostMapping(path = "projects/{projectId}/analyze/reset")
    public ResponseEntity resetAnalysis(@PathVariable("projectId") Long projectId) {
        try {
            resetAnalysisUseCase.resetAnalysis(projectId);
        } catch (ProjectNotFoundException e) {
            return new ResponseEntity(new ErrorMessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
