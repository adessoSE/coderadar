package io.reflectoring.coderadar.useradministration.service.teams.get;

import io.reflectoring.coderadar.useradministration.domain.Team;
import io.reflectoring.coderadar.useradministration.port.driven.GetTeamPort;
import io.reflectoring.coderadar.useradministration.TeamNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driver.teams.get.GetTeamUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetTeamService implements GetTeamUseCase {

    private final GetTeamPort getTeamPort;

    public GetTeamService(GetTeamPort getTeamPort) {
        this.getTeamPort = getTeamPort;
    }

    @Override
    public Team get(long teamId) {
        if(getTeamPort.existsById(teamId)){
            return getTeamPort.get(teamId);
        } else {
            throw new TeamNotFoundException(teamId);
        }
    }
}
