package io.reflectoring.coderadar.rest.dependencymap;

import io.reflectoring.coderadar.dependencymap.port.driver.GetCompareTreeUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
@RequiredArgsConstructor
public class DependencyCompareTreeController implements AbstractBaseController {
  private final GetCompareTreeUseCase getCompareTreeUseCase;
  private final AuthenticationService authenticationService;

  @GetMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      value = "/analyzers/{projectId}/structureMap/{commitName}/{secondCommit}")
  public ResponseEntity<Object> getDependencyTree(
      @PathVariable("projectId") Long projectId,
      @PathVariable("commitName") String commitName,
      @PathVariable("secondCommit") String secondCommit) {
    authenticationService.authenticateMember(projectId);
    return ResponseEntity.ok(
        getCompareTreeUseCase.getDependencyTree(projectId, commitName, secondCommit));
  }
}
