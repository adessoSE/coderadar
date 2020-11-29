package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.query.domain.CommitLog;
import io.reflectoring.coderadar.query.port.driver.GetCommitLogUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetCommitLogController implements AbstractBaseController {
  private final GetCommitLogUseCase getCommitLogUseCase;
  private final AuthenticationService authenticationService;

  @GetMapping(path = "/projects/{projectId}/commitLog", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<CommitLog>> getCommitLog(@PathVariable Long projectId) {
    authenticationService.authenticateMember(projectId);
    return new ResponseEntity<>(getCommitLogUseCase.getCommitLog(projectId), HttpStatus.OK);
  }
}
