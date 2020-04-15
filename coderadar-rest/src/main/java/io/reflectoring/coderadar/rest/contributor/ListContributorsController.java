package io.reflectoring.coderadar.rest.contributor;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.contributor.port.driver.GetContributorsForPathCommand;
import io.reflectoring.coderadar.contributor.port.driver.ListContributorsUseCase;
import io.reflectoring.coderadar.rest.domain.GetContributorResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional
public class ListContributorsController {
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
        map(listContributorsUseCase.listContributors(projectId)), HttpStatus.OK);
  }

  @RequestMapping(
      method = {RequestMethod.GET, RequestMethod.POST},
      path = "/projects/{projectId}/contributors/path",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<GetContributorResponse>> listContributorsForFile(
      @PathVariable long projectId, @RequestBody @Validated GetContributorsForPathCommand command) {
    return new ResponseEntity<>(
        map(listContributorsUseCase.listContributorsForProjectAndPathInCommit(projectId, command)),
        HttpStatus.OK);
  }

  private List<GetContributorResponse> map(List<Contributor> contributors) {
    List<GetContributorResponse> result = new ArrayList<>(contributors.size());
    for (Contributor c : contributors) {
      GetContributorResponse responseItem = new GetContributorResponse();
      responseItem.setId(c.getId());
      responseItem.setDisplayName(c.getDisplayName());
      responseItem.setNames(c.getNames());
      responseItem.setEmailAddresses(c.getEmailAddresses());
      result.add(responseItem);
    }
    return result;
  }
}
