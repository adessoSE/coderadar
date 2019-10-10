package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.query.port.driver.GetCommitsInProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class GetCommitsInProjectController {
  private final GetCommitsInProjectUseCase getCommitsInProjectUseCase;

  @Autowired
  public GetCommitsInProjectController(GetCommitsInProjectUseCase getCommitsInProjectUseCase) {
    this.getCommitsInProjectUseCase = getCommitsInProjectUseCase;
  }

  @GetMapping(path = "/projects/{projectId}/commits")
  public ResponseEntity listCommits(@PathVariable("projectId") Long projectId) {
    return new ResponseEntity<>(getCommitsInProjectUseCase.get(projectId), HttpStatus.OK);
  }
}
