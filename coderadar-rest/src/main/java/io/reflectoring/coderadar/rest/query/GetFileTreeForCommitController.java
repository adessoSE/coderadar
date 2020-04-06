package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.query.domain.FileTree;
import io.reflectoring.coderadar.query.port.driver.GetFileTreeForCommitUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class GetFileTreeForCommitController {

  private final GetFileTreeForCommitUseCase getFileTreeForCommitUseCase;

  public GetFileTreeForCommitController(GetFileTreeForCommitUseCase getFileTreeForCommitUseCase) {
    this.getFileTreeForCommitUseCase = getFileTreeForCommitUseCase;
  }

  @GetMapping(
      path = "/projects/{projectId}/files/tree/{commitHash}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<FileTree> getMetricValues(
      @PathVariable("projectId") long projectId, @PathVariable("commitHash") String commitHash) {
    return new ResponseEntity<>(
        getFileTreeForCommitUseCase.getFileTreeForCommit(projectId, commitHash), HttpStatus.OK);
  }
}
