package io.reflectoring.coderadar.useradministration.service.teams;

import io.reflectoring.coderadar.useradministration.port.driver.teams.AddUsersToTeamUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddUsersToTeamService implements AddUsersToTeamUseCase {

    @Override
    public void addUsersToTeam(long teamId, List<Long> userIds) {

    }
}
