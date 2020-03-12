package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.query.domain.ContributorsForFile;
import io.reflectoring.coderadar.query.port.driver.GetCriticalFilesUseCase;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
      @PathVariable Long projectId, @RequestParam(defaultValue = "1") int numOfContr) {
    return new ResponseEntity<>(
        getCriticalFilesUseCase.getCriticalFiles(projectId, numOfContr), HttpStatus.OK);
  }
}
