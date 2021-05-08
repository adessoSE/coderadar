package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.domain.FileTree;
import io.reflectoring.coderadar.query.port.driver.GetFileTreeForCommitUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetFileTreeForCommitController implements AbstractBaseController {
  private final GetFileTreeForCommitUseCase getFileTreeForCommitUseCase;
  private final AuthenticationService authenticationService;

  @GetMapping(
      path = "/projects/{projectId}/files/tree/{commitHash}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<FileTree> getFileTreeForCommit(
      @PathVariable("projectId") long projectId,
      @PathVariable("commitHash") String commitHash,
      @RequestParam("changedOnly") boolean changedFilesOnly) {
    authenticationService.authenticateMember(projectId);
    return new ResponseEntity<>(
        getFileTreeForCommitUseCase.getFileTreeForCommit(projectId, commitHash, changedFilesOnly),
        HttpStatus.OK);
  }
}
