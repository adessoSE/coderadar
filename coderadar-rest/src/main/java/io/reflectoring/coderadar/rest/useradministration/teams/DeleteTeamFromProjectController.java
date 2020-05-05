package io.reflectoring.coderadar.rest.useradministration.teams;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.port.driver.teams.RemoveTeamFromProjectUseCase;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class DeleteTeamFromProjectController implements AbstractBaseController {
  private final RemoveTeamFromProjectUseCase removeTeamFromProjectUseCase;

  public DeleteTeamFromProjectController(
      RemoveTeamFromProjectUseCase removeTeamFromProjectUseCase) {
    this.removeTeamFromProjectUseCase = removeTeamFromProjectUseCase;
  }
}
