package io.reflectoring.coderadar.graph.useradministration.adapter.teams;

import io.reflectoring.coderadar.graph.useradministration.domain.TeamEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.TeamRepository;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.useradministration.port.driven.CreateTeamPort;
import io.reflectoring.coderadar.useradministration.port.driver.teams.create.CreateTeamCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTeamAdapter implements CreateTeamPort {

  private final TeamRepository teamRepository;
  private final UserRepository userRepository;

  @Override
  public Long createTeam(CreateTeamCommand createTeamCommand) {
    TeamEntity teamEntity = new TeamEntity();
    teamEntity.setMembers(userRepository.findAllByIds(createTeamCommand.getUserIds()));
    teamEntity.setName(createTeamCommand.getName());
    return teamRepository.save(teamEntity, 1).getId();
  }
}
