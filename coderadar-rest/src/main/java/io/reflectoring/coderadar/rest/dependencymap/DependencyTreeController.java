package io.reflectoring.coderadar.rest.dependencymap;

import io.reflectoring.coderadar.dependencymap.port.driver.GetDependencyTreeUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
public class DependencyTreeController implements AbstractBaseController {

  private final GetDependencyTreeUseCase getDependencyTreeUseCase;
  private final AuthenticationService authenticationService;

  @Autowired
  public DependencyTreeController(
      GetDependencyTreeUseCase getDependencyTreeUseCase,
      AuthenticationService authenticationService) {
    this.getDependencyTreeUseCase = getDependencyTreeUseCase;
    this.authenticationService = authenticationService;
  }

  @GetMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      value = "/analyzers/{projectId}/structureMap/{commitName}")
  public ResponseEntity<Object> getDependencyTree(
      @PathVariable("projectId") Long projectId, @PathVariable("commitName") String commitName) {
    authenticationService.authenticateMember(projectId);
    return ResponseEntity.ok(getDependencyTreeUseCase.getDependencyTree(projectId, commitName));
  }
}
