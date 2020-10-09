package io.reflectoring.coderadar.rest.module;

import static io.reflectoring.coderadar.rest.GetModuleResponseMapper.mapModule;

import io.reflectoring.coderadar.projectadministration.port.driver.module.get.GetModuleUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.GetModuleResponse;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
@RequiredArgsConstructor
public class GetModuleController implements AbstractBaseController {
  private final GetModuleUseCase getModuleUseCase;
  private final AuthenticationService authenticationService;

  @GetMapping(
      path = "/projects/{projectId}/modules/{moduleId}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<GetModuleResponse> getModule(
      @PathVariable(name = "projectId") long projectId,
      @PathVariable(name = "moduleId") long moduleId) {
    authenticationService.authenticateMember(projectId);
    return new ResponseEntity<>(mapModule(getModuleUseCase.get(moduleId)), HttpStatus.OK);
  }
}
