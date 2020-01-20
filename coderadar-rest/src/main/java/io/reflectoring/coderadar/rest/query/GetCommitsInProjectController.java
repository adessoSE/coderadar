package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.query.port.driver.GetCommitsInProjectUseCase;
import io.reflectoring.coderadar.rest.domain.GetCommitResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@Transactional
public class GetCommitsInProjectController {
  private final GetCommitsInProjectUseCase getCommitsInProjectUseCase;

  public GetCommitsInProjectController(GetCommitsInProjectUseCase getCommitsInProjectUseCase) {
    this.getCommitsInProjectUseCase = getCommitsInProjectUseCase;
  }

  @GetMapping(path = "/projects/{projectId}/commits", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<GetCommitResponse>> listCommits(@PathVariable("projectId") Long projectId, Pageable pageable) {
    return new ResponseEntity<>(getCommitsInProjectUseCase.getPaged(projectId, pageable).map(commit -> new GetCommitResponse()
            .setName(commit.getName())
            .setAnalyzed(commit.isAnalyzed())
            .setAuthor(commit.getAuthor())
            .setComment(commit.getComment())
            .setTimestamp(commit.getTimestamp())), HttpStatus.OK);
  }
}
