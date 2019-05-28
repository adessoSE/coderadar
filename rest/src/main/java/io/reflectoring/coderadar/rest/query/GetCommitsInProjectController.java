package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.core.query.port.driver.GetCommitResponse;
import io.reflectoring.coderadar.core.query.port.driver.GetCommitsInProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetCommitsInProjectController {
    private final GetCommitsInProjectUseCase getCommitsInProjectUseCase;

    @Autowired
    public GetCommitsInProjectController(GetCommitsInProjectUseCase getCommitsInProjectUseCase) {
        this.getCommitsInProjectUseCase = getCommitsInProjectUseCase;
    }

    @GetMapping(path = "/projects/{projectId}/commits")
    public ResponseEntity<List<GetCommitResponse>> listCommits(@PathVariable("projectId") Long projectId) {
        return new ResponseEntity<>(getCommitsInProjectUseCase.get(projectId), HttpStatus.OK);
    }
}
