package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.query.port.driver.GetMetricValuesOfTwoCommitsUseCase;
import io.reflectoring.coderadar.query.port.driver.GetMetricsForTwoCommitsCommand;
import io.reflectoring.coderadar.rest.ErrorMessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class GetMetricValuesOfTwoCommitsController {

    private final GetMetricValuesOfTwoCommitsUseCase getMetricValuesOfTwoCommitsUseCase;

    public GetMetricValuesOfTwoCommitsController(GetMetricValuesOfTwoCommitsUseCase getMetricValuesOfTwoCommitsUseCase) {
        this.getMetricValuesOfTwoCommitsUseCase = getMetricValuesOfTwoCommitsUseCase;
    }

    @GetMapping(path = "/projects/{projectId}/metricvalues/deltaTree", consumes = "application/json", produces = "application/json")
    public ResponseEntity getMetricValuesForTwoCommits(@Validated @RequestBody GetMetricsForTwoCommitsCommand command, @PathVariable("projectId") Long projectId){
        try {
            return new ResponseEntity<>(getMetricValuesOfTwoCommitsUseCase.get(command, projectId), HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
