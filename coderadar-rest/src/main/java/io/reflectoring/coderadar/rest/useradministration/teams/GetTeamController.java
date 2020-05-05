package io.reflectoring.coderadar.rest.useradministration.teams;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.port.driver.teams.get.GetTeamUseCase;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class GetTeamController implements AbstractBaseController {
  private final GetTeamUseCase getTeamUseCase;

  public GetTeamController(GetTeamUseCase getTeamUseCase) {
    this.getTeamUseCase = getTeamUseCase;
  }
}
