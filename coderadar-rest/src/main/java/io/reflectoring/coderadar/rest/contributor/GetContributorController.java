package io.reflectoring.coderadar.rest.contributor;

import static io.reflectoring.coderadar.rest.GetContributorResponseMapper.mapContributor;

import io.reflectoring.coderadar.contributor.port.driver.GetContributorUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.GetContributorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetContributorController implements AbstractBaseController {
  private final GetContributorUseCase getContributorUseCase;

  @GetMapping(path = "/contributors/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<GetContributorResponse> getById(@PathVariable long id) {
    return new ResponseEntity<>(mapContributor(getContributorUseCase.getById(id)), HttpStatus.OK);
  }
}
