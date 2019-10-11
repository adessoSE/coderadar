package io.reflectoring.coderadar.rest.module;

import io.reflectoring.coderadar.projectadministration.port.driver.module.get.GetModuleUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class GetModuleController {
  private final GetModuleUseCase getModuleUseCase;

  @Autowired
  public GetModuleController(GetModuleUseCase getModuleUseCase) {
    this.getModuleUseCase = getModuleUseCase;
  }

  @GetMapping(path = "/projects/{projectId}/modules/{moduleId}")
  public ResponseEntity getModule(@PathVariable(name = "moduleId") Long moduleId) {
    return new ResponseEntity<>(getModuleUseCase.get(moduleId), HttpStatus.OK);
  }
}
