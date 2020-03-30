package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.query.domain.ContributorsForFile;
import io.reflectoring.coderadar.query.domain.FileAndCommitsForTimePeriod;
import io.reflectoring.coderadar.query.port.driver.GetCriticalFilesUseCase;
import io.reflectoring.coderadar.query.port.driver.GetFilesWithManyContributorsCommand;
import io.reflectoring.coderadar.query.port.driver.GetFrequentlyChangedFilesCommand;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional
public class GetCriticalFilesController {
  private final GetCriticalFilesUseCase getCriticalFilesUseCase;

  public GetCriticalFilesController(GetCriticalFilesUseCase getCriticalFilesUseCase) {
    this.getCriticalFilesUseCase = getCriticalFilesUseCase;
  }

  @GetMapping(
      path = "/projects/{projectId}/files/critical",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<ContributorsForFile>> getFilesWithManyContributors(
      @PathVariable Long projectId,
      @RequestBody @Validated GetFilesWithManyContributorsCommand command) {
    return new ResponseEntity<>(
        getCriticalFilesUseCase.getFilesWithManyContributors(projectId, command), HttpStatus.OK);
  }

  @GetMapping(path = "/projects/{projectId}/files/modification/frequency")
  public ResponseEntity<List<FileAndCommitsForTimePeriod>> getFrequentlyChangedFiles(
      @PathVariable long projectId,
      @RequestBody @Validated GetFrequentlyChangedFilesCommand command) {
    return new ResponseEntity<>(
        getCriticalFilesUseCase.getFrequentlyChangedFiles(projectId, command), HttpStatus.OK);
  }
}
