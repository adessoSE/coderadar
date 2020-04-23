package io.reflectoring.coderadar.rest.contributor;

import io.reflectoring.coderadar.contributor.port.driver.MergeContributorsCommand;
import io.reflectoring.coderadar.contributor.port.driver.MergeContributorsUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
public class MergeContributorsController extends AbstractBaseController {
  private final MergeContributorsUseCase mergeContributorsUseCase;

  public MergeContributorsController(MergeContributorsUseCase mergeContributorsUseCase) {
    this.mergeContributorsUseCase = mergeContributorsUseCase;
  }

  @PostMapping(
      path = "/contributors/merge",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<HttpStatus> mergeContributors(
      @RequestBody @Validated MergeContributorsCommand command) {
    mergeContributorsUseCase.mergeContributors(command);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
