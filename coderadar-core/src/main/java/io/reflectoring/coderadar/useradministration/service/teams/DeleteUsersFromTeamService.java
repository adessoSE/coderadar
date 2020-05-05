package io.reflectoring.coderadar.useradministration.service.teams;

import io.reflectoring.coderadar.useradministration.port.driver.teams.DeleteUsersFromTeamUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeleteUsersFromTeamService implements DeleteUsersFromTeamUseCase {
    @Override
    public void deleteUsersFromTeam(long teamId, List<Long> userIds) {

    }
}
