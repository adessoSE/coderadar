package io.reflectoring.coderadar.useradministration.service.teams.get;

import io.reflectoring.coderadar.domain.Team;
import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserPort;
import io.reflectoring.coderadar.useradministration.port.driven.ListTeamsForUserPort;
import io.reflectoring.coderadar.useradministration.port.driver.teams.get.ListTeamsForUserUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListTeamsForUserService implements ListTeamsForUserUseCase {

  private final GetUserPort getUserPort;
  private final ListTeamsForUserPort listTeamsForUserPort;

  @Override
  public List<Team> listTeamsForUser(long userId) {
    if (getUserPort.existsById(userId)) {
      return listTeamsForUserPort.listTeamsForUser(userId);
    } else {
      throw new UserNotFoundException(userId);
    }
  }
}
