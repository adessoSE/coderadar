package io.reflectoring.coderadar.rest.module;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.ListModulesOfProjectCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.ListModulesOfProjectUseCase;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/projects/{projectId}/modules")
public class ListModulesOfProjectController {
  private final ListModulesOfProjectUseCase listModulesOfProjectUseCase;

  @Autowired
  public ListModulesOfProjectController(ListModulesOfProjectUseCase listModulesOfProjectUseCase) {
    this.listModulesOfProjectUseCase = listModulesOfProjectUseCase;
  }

  @GetMapping
  public ResponseEntity<List<Module>> listModules(
      @RequestBody ListModulesOfProjectCommand command) {
    return new ResponseEntity<>(listModulesOfProjectUseCase.listModules(command), HttpStatus.OK);
  }
}
