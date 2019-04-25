package io.reflectoring.coderadar.rest.module;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.GetModuleUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/projects/{projectId}/modules")
public class GetModuleController {
  private final GetModuleUseCase getModuleUseCase;

  @Autowired
  public GetModuleController(GetModuleUseCase getModuleUseCase) {
    this.getModuleUseCase = getModuleUseCase;
  }

  @GetMapping("/{moduleId}")
  public ResponseEntity<Module> getModule(@PathVariable Long moduleId) {
    return new ResponseEntity<>(getModuleUseCase.get(moduleId), HttpStatus.OK);
  }
}
