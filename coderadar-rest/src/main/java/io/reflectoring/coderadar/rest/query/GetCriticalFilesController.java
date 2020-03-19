package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.contributor.port.driver.GetCriticalFilesCommand;
import io.reflectoring.coderadar.query.domain.ContributorsForFile;
import io.reflectoring.coderadar.query.port.driver.GetCriticalFilesUseCase;
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
  public ResponseEntity<List<ContributorsForFile>> getCriticalFiles(
      @PathVariable Long projectId, @RequestBody @Validated GetCriticalFilesCommand command) {
    return new ResponseEntity<>(
        getCriticalFilesUseCase.getCriticalFiles(projectId, command), HttpStatus.OK);
  }
}
