package io.reflectoring.coderadar.rest.contributor;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.contributor.port.driver.GetContributorsUseCase;
import io.reflectoring.coderadar.contributor.port.driver.GetForFilenameCommand;
import io.reflectoring.coderadar.rest.domain.GetContributorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Transactional
public class GetContributorsController {
    private final GetContributorsUseCase getContributorsUseCase;

    public GetContributorsController(GetContributorsUseCase getContributorsUseCase) {
        this.getContributorsUseCase = getContributorsUseCase;
    }

    @GetMapping(path = "/projects/{projectId}/contributors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GetContributorResponse>> getContributors(@PathVariable Long projectId) {
        return new ResponseEntity<>(map(getContributorsUseCase.getContributors(projectId)), HttpStatus.OK);
    }

    @GetMapping(path = "/projects/{projectId}/contributors/file", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GetContributorResponse>> getContributorsForFile(@PathVariable Long projectId, @RequestBody @Validated GetForFilenameCommand command) {
        return new ResponseEntity<>(map(getContributorsUseCase.getContributorsForProjectAndFilename(projectId, command)), HttpStatus.OK);
    }

    // maps the domain objects we get from the core to the Response objects we need to return
    private List<GetContributorResponse> map(List<Contributor> contributors) {
        List<GetContributorResponse> responseList = new ArrayList<>(contributors.size());
        for (Contributor c : contributors) {
            GetContributorResponse response = new GetContributorResponse(c.getId(), c.getName(), c.getEmails());
            responseList.add(response);
        }
        return responseList;
    }
}
