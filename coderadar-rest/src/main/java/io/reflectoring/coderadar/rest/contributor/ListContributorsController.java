package io.reflectoring.coderadar.rest.contributor;

import static io.reflectoring.coderadar.rest.GetContributorResponseMapper.mapContributors;

import io.reflectoring.coderadar.contributor.port.driver.GetContributorsForPathCommand;
import io.reflectoring.coderadar.contributor.port.driver.ListContributorsUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.GetContributorResponse;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Transactional
@RestController
public class ListContributorsController implements AbstractBaseController {
  private final ListContributorsUseCase listContributorsUseCase;
  private final AuthenticationService authenticationService;

  public ListContributorsController(
      ListContributorsUseCase listContributorsUseCase,
      AuthenticationService authenticationService) {
    this.listContributorsUseCase = listContributorsUseCase;
    this.authenticationService = authenticationService;
  }

  @GetMapping(
      path = "/projects/{projectId}/contributors",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<GetContributorResponse>> listContributors(
      @PathVariable long projectId) {
    authenticationService.authenticateMember(projectId);
    return new ResponseEntity<>(
        mapContributors(listContributorsUseCase.listContributors(projectId)), HttpStatus.OK);
  }

  @RequestMapping(
      method = {RequestMethod.GET, RequestMethod.POST},
      path = "/projects/{projectId}/contributors/path",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<GetContributorResponse>> listContributorsForFile(
      @PathVariable long projectId, @RequestBody @Validated GetContributorsForPathCommand command) {
    return new ResponseEntity<>(
        mapContributors(
            listContributorsUseCase.listContributorsForProjectAndPathInCommit(projectId, command)),
        HttpStatus.OK);
  }
}
