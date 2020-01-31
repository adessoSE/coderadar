package io.reflectoring.coderadar.rest.contributor;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.contributor.port.driver.GetContributorsUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
public class GetContributorsController {
    private final GetContributorsUseCase getContributorsUseCase;

    public GetContributorsController(GetContributorsUseCase getContributorsUseCase) {
        this.getContributorsUseCase = getContributorsUseCase;
    }

    @GetMapping(path = "/projects/{projectId}/contributors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Contributor>> getContributors(@PathVariable Long projectId) {
        return new ResponseEntity<>(getContributorsUseCase.getContributors(projectId), HttpStatus.OK);
    }
}
