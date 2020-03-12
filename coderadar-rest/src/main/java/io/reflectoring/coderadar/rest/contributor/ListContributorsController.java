package io.reflectoring.coderadar.rest.contributor;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.contributor.port.driver.GetContributorsForFileCommand;
import io.reflectoring.coderadar.contributor.port.driver.ListContributorsUseCase;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
  public ResponseEntity<List<Contributor>> listContributors(@PathVariable long projectId) {
    return new ResponseEntity<>(listContributorsUseCase.listContributors(projectId), HttpStatus.OK);
  }

  @GetMapping(
      path = "/projects/{projectId}/contributors/file",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Contributor>> listContributorsForFile(
      @PathVariable long projectId, @RequestBody @Validated GetContributorsForFileCommand command) {
    return new ResponseEntity<>(
        listContributorsUseCase.listContributorsForProjectAndFilename(projectId, command),
        HttpStatus.OK);
  }
}
