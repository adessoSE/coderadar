package io.reflectoring.coderadar.rest.module;

import io.reflectoring.coderadar.projectadministration.port.driver.module.get.GetModuleUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.GetModuleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
public class GetModuleController extends AbstractBaseController {
  private final GetModuleUseCase getModuleUseCase;

  public GetModuleController(GetModuleUseCase getModuleUseCase) {
    this.getModuleUseCase = getModuleUseCase;
  }

  @GetMapping(
      path = "/projects/{projectId}/modules/{moduleId}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<GetModuleResponse> getModule(
      @PathVariable(name = "moduleId") long moduleId) {
    return new ResponseEntity<>(
        new GetModuleResponse(moduleId, getModuleUseCase.get(moduleId).getPath()), HttpStatus.OK);
  }
}
