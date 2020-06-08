package io.reflectoring.coderadar.rest.contributor;

import io.reflectoring.coderadar.contributor.port.driver.GetContributorsForPathCommand;
import io.reflectoring.coderadar.contributor.port.driver.ListContributorsUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.GetContributorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.reflectoring.coderadar.rest.GetContributorResponseMapper.mapContributors;

@Transactional
@RestController
public class ListContributorsController implements AbstractBaseController {
  private final ListContributorsUseCase listContributorsUseCase;

  public ListContributorsController(ListContributorsUseCase listContributorsUseCase) {
    this.listContributorsUseCase = listContributorsUseCase;
  }

  @GetMapping(
      path = "/projects/{projectId}/contributors",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<GetContributorResponse>> listContributors(
      @PathVariable long projectId) {
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
