package io.reflectoring.coderadar.rest.project;

import io.reflectoring.coderadar.projectadministration.port.driver.project.update.UpdateProjectCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.update.UpdateProjectUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import java.net.MalformedURLException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
@RequiredArgsConstructor
public class UpdateProjectController implements AbstractBaseController {
  private final UpdateProjectUseCase updateProjectUseCase;
  private final AuthenticationService authenticationService;

  @PostMapping(path = "/projects/{projectId}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<HttpStatus> updateProject(
      @RequestBody @Validated UpdateProjectCommand command,
      @PathVariable(name = "projectId") long projectId)
      throws MalformedURLException {
    authenticationService.authenticateAdmin(projectId);
    updateProjectUseCase.update(command, projectId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
