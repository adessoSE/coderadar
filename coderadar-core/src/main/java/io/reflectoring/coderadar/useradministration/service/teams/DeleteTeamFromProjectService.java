package io.reflectoring.coderadar.useradministration.service.teams;

import io.reflectoring.coderadar.useradministration.TeamNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driven.DeleteTeamFromProjectPort;
import io.reflectoring.coderadar.useradministration.port.driven.GetTeamPort;
import io.reflectoring.coderadar.useradministration.port.driver.teams.DeleteTeamFromProjectUseCase;
import org.springframework.stereotype.Service;

@Service
public class DeleteTeamFromProjectService implements DeleteTeamFromProjectUseCase {

    private final GetTeamPort getTeamPort;
    private final DeleteTeamFromProjectPort deleteTeamFromProjectPort;

    public DeleteTeamFromProjectService(GetTeamPort getTeamPort, DeleteTeamFromProjectPort deleteTeamFromProjectPort) {
        this.getTeamPort = getTeamPort;
        this.deleteTeamFromProjectPort = deleteTeamFromProjectPort;
    }

    @Override
    public void deleteTeam(long teamId) {
        if(getTeamPort.existsById(teamId)){
            deleteTeamFromProjectPort.deleteTeam(teamId);
        } else {
            throw new TeamNotFoundException(teamId);
        }
    }
}
