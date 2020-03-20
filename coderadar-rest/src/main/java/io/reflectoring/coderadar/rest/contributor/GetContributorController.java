package io.reflectoring.coderadar.rest.contributor;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.contributor.port.driver.GetContributorUseCase;
import io.reflectoring.coderadar.rest.domain.GetContributorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class GetContributorController {
  private final GetContributorUseCase getContributorUseCase;

  public GetContributorController(GetContributorUseCase getContributorUseCase) {
    this.getContributorUseCase = getContributorUseCase;
  }

  @GetMapping(path = "/contributors/{id}")
  public ResponseEntity<GetContributorResponse> getById(@PathVariable long id) {
    return new ResponseEntity<>(map(getContributorUseCase.getById(id)), HttpStatus.OK);
  }

  private GetContributorResponse map(Contributor contributor) {
    return new GetContributorResponse(
        contributor.getId(),
        contributor.getDisplayName(),
        contributor.getNames(),
        contributor.getEmailAddresses());
  }
}
