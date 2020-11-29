package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.query.domain.CommitResponse;
import io.reflectoring.coderadar.query.port.driver.GetCommitsInProjectUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.GetCommitResponseMapper;
import io.reflectoring.coderadar.rest.domain.GetCommitResponse;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
@RequiredArgsConstructor
public class GetCommitsInProjectController implements AbstractBaseController {
  private final GetCommitsInProjectUseCase getCommitsInProjectUseCase;
  private final AuthenticationService authenticationService;

  @GetMapping(
      path = "/projects/{projectId}/{branchName}/commits",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CommitResponse[]> listCommitsForBranch(
      @PathVariable("projectId") long projectId,
      @PathVariable("branchName") String branchName,
      @RequestParam(value = "email", required = false) String email) {
    authenticationService.authenticateMember(projectId);
    CommitResponse[] commits;
    //if (email == null || email.isEmpty()) {
      commits = getCommitsInProjectUseCase.getResponses(projectId, branchName);
   // } else {
      //commits = getCommitsInProjectUseCase.getForContributor(projectId, branchName, email);
  //  }
   // List<GetCommitResponse> result = GetCommitResponseMapper.mapCommits(commits);
    return new ResponseEntity<>(commits, HttpStatus.OK);
  }
}
