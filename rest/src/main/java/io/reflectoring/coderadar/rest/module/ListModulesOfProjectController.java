package io.reflectoring.coderadar.rest.module;

import io.reflectoring.coderadar.core.projectadministration.port.driver.module.get.GetModuleResponse;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.get.ListModulesOfProjectUseCase;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ListModulesOfProjectController {
  private final ListModulesOfProjectUseCase listModulesOfProjectUseCase;

  @Autowired
  public ListModulesOfProjectController(ListModulesOfProjectUseCase listModulesOfProjectUseCase) {
    this.listModulesOfProjectUseCase = listModulesOfProjectUseCase;
  }

  @GetMapping(path = "/projects/{projectId}/modules")
  public ResponseEntity<List<GetModuleResponse>> listModules(@PathVariable Long projectId) {
    return new ResponseEntity<>(listModulesOfProjectUseCase.listModules(projectId), HttpStatus.OK);
  }
}
