package io.reflectoring.coderadar.rest.project;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.GetProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
public class GetProjectController {
  private final GetProjectUseCase getProjectUseCase;

  @Autowired
  public GetProjectController(GetProjectUseCase getProjectUseCase) {
    this.getProjectUseCase = getProjectUseCase;
  }

  @GetMapping("/{projectId}")
  public ResponseEntity<Project> getProject(@PathVariable Long projectId) {
    return new ResponseEntity<>(getProjectUseCase.get(projectId), HttpStatus.OK);
  }
}
