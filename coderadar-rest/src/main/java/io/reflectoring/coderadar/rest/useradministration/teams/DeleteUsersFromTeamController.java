package io.reflectoring.coderadar.rest.useradministration.teams;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.port.driver.teams.DeleteUsersFromTeamUseCase;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class DeleteUsersFromTeamController implements AbstractBaseController {
  private final DeleteUsersFromTeamUseCase deleteUsersFromTeamUseCase;

  public DeleteUsersFromTeamController(DeleteUsersFromTeamUseCase deleteUsersFromTeamUseCase) {
    this.deleteUsersFromTeamUseCase = deleteUsersFromTeamUseCase;
  }
}
