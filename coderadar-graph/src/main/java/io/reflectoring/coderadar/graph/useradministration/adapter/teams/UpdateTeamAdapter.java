package io.reflectoring.coderadar.graph.useradministration.adapter.teams;

import io.reflectoring.coderadar.graph.useradministration.domain.TeamEntity;
import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.TeamRepository;
import io.reflectoring.coderadar.useradministration.TeamNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driven.UpdateTeamPort;
import io.reflectoring.coderadar.useradministration.port.driver.teams.update.UpdateTeamCommand;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateTeamAdapter implements UpdateTeamPort {

  private final TeamRepository teamRepository;

  @Override
  public void updateTeam(long teamId, UpdateTeamCommand updateTeamCommand) {
    TeamEntity teamEntity =
        teamRepository.findById(teamId, 1).orElseThrow(() -> new TeamNotFoundException(teamId));
    teamRepository.deleteUsersFromTeam(
        teamId,
        teamEntity.getMembers().stream()
            .mapToLong(UserEntity::getId)
            .boxed()
            .collect(Collectors.toList()));
    teamRepository.addUsersToTeam(teamId, updateTeamCommand.getUserIds());
    teamEntity.setName(updateTeamCommand.getName());
    teamRepository.save(teamEntity, 0);
  }
}
