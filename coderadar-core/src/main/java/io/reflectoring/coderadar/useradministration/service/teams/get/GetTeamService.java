package io.reflectoring.coderadar.useradministration.service.teams.get;

import io.reflectoring.coderadar.useradministration.domain.Team;
import io.reflectoring.coderadar.useradministration.port.driver.teams.get.GetTeamUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetTeamService implements GetTeamUseCase {
    @Override
    public Team get(long teamId) {
        return null;
    }
}
