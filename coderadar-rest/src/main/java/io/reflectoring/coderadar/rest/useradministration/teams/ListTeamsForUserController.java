package io.reflectoring.coderadar.rest.useradministration.teams;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.port.driver.teams.get.ListTeamsForUserUseCase;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class ListTeamsForUserController implements AbstractBaseController {
  private final ListTeamsForUserUseCase listTeamsForUserUseCase;

  public ListTeamsForUserController(ListTeamsForUserUseCase listTeamsForUserUseCase) {
    this.listTeamsForUserUseCase = listTeamsForUserUseCase;
  }
}
