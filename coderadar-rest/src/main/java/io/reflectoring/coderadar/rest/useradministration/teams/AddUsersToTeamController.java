package io.reflectoring.coderadar.rest.useradministration.teams;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.port.driver.teams.AddUsersToTeamUseCase;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class AddUsersToTeamController implements AbstractBaseController {
  private final AddUsersToTeamUseCase addUsersToTeamUseCase;

  public AddUsersToTeamController(AddUsersToTeamUseCase addUsersToTeamUseCase) {
    this.addUsersToTeamUseCase = addUsersToTeamUseCase;
  }
}
