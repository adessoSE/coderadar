package io.reflectoring.coderadar.rest.unit.module;

import io.reflectoring.coderadar.core.projectadministration.port.driver.module.get.GetModuleResponse;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.get.GetModuleUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetModuleController {
  private final GetModuleUseCase getModuleUseCase;

  @Autowired
  public GetModuleController(GetModuleUseCase getModuleUseCase) {
    this.getModuleUseCase = getModuleUseCase;
  }

  @GetMapping(path = "/projects/{projectId}/modules/{moduleId}")
  public ResponseEntity<GetModuleResponse> getModule(
      @PathVariable(name = "moduleId") Long moduleId) {
    return new ResponseEntity<>(getModuleUseCase.get(moduleId), HttpStatus.OK);
  }
}
