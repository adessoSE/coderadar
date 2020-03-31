package io.reflectoring.coderadar.rest.query;

import static io.reflectoring.coderadar.rest.GetCommitResponseMapper.mapCommits;

import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.query.port.driver.GetCommitsInProjectUseCase;
import io.reflectoring.coderadar.rest.domain.GetCommitResponse;
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
public class GetCommitsInProjectController {
  private final GetCommitsInProjectUseCase getCommitsInProjectUseCase;

  public GetCommitsInProjectController(GetCommitsInProjectUseCase getCommitsInProjectUseCase) {
    this.getCommitsInProjectUseCase = getCommitsInProjectUseCase;
  }

  @GetMapping(
      path = "/projects/{projectId}/{branchName}/commits",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<GetCommitResponse>> listCommits(
      @PathVariable("projectId") long projectId, @PathVariable("branchName") String branchName) {
    List<Commit> commits = getCommitsInProjectUseCase.get(projectId, branchName);
    List<GetCommitResponse> result = mapCommits(commits);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
