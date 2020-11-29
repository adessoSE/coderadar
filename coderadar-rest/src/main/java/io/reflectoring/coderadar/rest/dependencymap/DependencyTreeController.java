package io.reflectoring.coderadar.rest.dependencymap;

import io.reflectoring.coderadar.dependencymap.port.driver.GetDependencyTreeUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DependencyTreeController implements AbstractBaseController {
  private final GetDependencyTreeUseCase getDependencyTreeUseCase;
  private final AuthenticationService authenticationService;

  @GetMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      value = "/analyzers/{projectId}/structureMap/{commitName}")
  public ResponseEntity<Object> getDependencyTree(
      @PathVariable("projectId") Long projectId, @PathVariable("commitName") String commitName) {
    authenticationService.authenticateMember(projectId);
    return ResponseEntity.ok(getDependencyTreeUseCase.getDependencyTree(projectId, commitName));
  }
}
