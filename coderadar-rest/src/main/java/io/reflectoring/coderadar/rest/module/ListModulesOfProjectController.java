package io.reflectoring.coderadar.rest.module;

import static io.reflectoring.coderadar.rest.GetModuleResponseMapper.mapModules;

import io.reflectoring.coderadar.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.ListModulesOfProjectUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.GetModuleResponse;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ListModulesOfProjectController implements AbstractBaseController {
  private final ListModulesOfProjectUseCase listModulesOfProjectUseCase;
  private final AuthenticationService authenticationService;

  @GetMapping(path = "/projects/{projectId}/modules", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<GetModuleResponse>> listModules(@PathVariable long projectId) {
    authenticationService.authenticateMember(projectId);
    List<Module> modules = listModulesOfProjectUseCase.listModules(projectId);
    return new ResponseEntity<>(mapModules(modules), HttpStatus.OK);
  }
}
