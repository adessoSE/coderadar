package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.query.port.driver.GetDeltaTreeForTwoCommitsCommand;
import io.reflectoring.coderadar.query.port.driver.GetDeltaTreeForTwoCommitsUseCase;
import io.reflectoring.coderadar.rest.domain.ErrorMessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional
public class GetDeltaTreeForTwoCommitsController {

    private final GetDeltaTreeForTwoCommitsUseCase getDeltaTreeForTwoCommitsUseCase;

    public GetDeltaTreeForTwoCommitsController(GetDeltaTreeForTwoCommitsUseCase getDeltaTreeForTwoCommitsUseCase) {
        this.getDeltaTreeForTwoCommitsUseCase = getDeltaTreeForTwoCommitsUseCase;
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET}, path = "/projects/{projectId}/metricvalues/deltaTree", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getDeltaTreeForTwoCommits(@Validated @RequestBody GetDeltaTreeForTwoCommitsCommand command, @PathVariable("projectId") Long projectId){
        try {
            return new ResponseEntity<>(getDeltaTreeForTwoCommitsUseCase.get(command, projectId), HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
