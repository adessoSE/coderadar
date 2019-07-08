package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.query.port.driver.GetCommitsInProjectUseCase;
import io.reflectoring.coderadar.rest.ErrorMessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetCommitsInProjectController {
  private final GetCommitsInProjectUseCase getCommitsInProjectUseCase;

  @Autowired
  public GetCommitsInProjectController(GetCommitsInProjectUseCase getCommitsInProjectUseCase) {
    this.getCommitsInProjectUseCase = getCommitsInProjectUseCase;
  }

  @GetMapping(path = "/projects/{projectId}/commits")
  public ResponseEntity listCommits(@PathVariable("projectId") Long projectId) {
    try {
      return new ResponseEntity<>(getCommitsInProjectUseCase.get(projectId), HttpStatus.OK);
    } catch (ProjectNotFoundException e) {
      return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }
  }
}
