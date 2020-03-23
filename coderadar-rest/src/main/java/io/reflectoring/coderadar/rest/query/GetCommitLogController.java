package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.query.domain.CommitLog;
import io.reflectoring.coderadar.query.port.driver.GetCommitLogUseCase;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class GetCommitLogController {

  private final GetCommitLogUseCase getCommitLogUseCase;

  public GetCommitLogController(GetCommitLogUseCase getCommitLogUseCase) {
    this.getCommitLogUseCase = getCommitLogUseCase;
  }

  @GetMapping(path = "/projects/{projectId}/commitLog", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<CommitLog>> getCommitLog(@PathVariable Long projectId) {
    return new ResponseEntity<>(getCommitLogUseCase.getCommitLog(projectId), HttpStatus.OK);
  }
}
