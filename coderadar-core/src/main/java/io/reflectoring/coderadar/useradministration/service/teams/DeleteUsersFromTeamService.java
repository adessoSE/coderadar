package io.reflectoring.coderadar.useradministration.service.teams;

import io.reflectoring.coderadar.useradministration.TeamNotFoundException;
import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driven.GetTeamPort;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserPort;
import io.reflectoring.coderadar.useradministration.port.driver.teams.DeleteUsersFromTeamUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeleteUsersFromTeamService implements DeleteUsersFromTeamUseCase {

    private final GetTeamPort getTeamPort;
    private final GetUserPort getUserPort;
    private final DeleteUsersFromTeamService deleteUsersFromTeamService;

    public DeleteUsersFromTeamService(GetTeamPort getTeamPort, GetUserPort getUserPort, DeleteUsersFromTeamService deleteUsersFromTeamService) {
        this.getTeamPort = getTeamPort;
        this.getUserPort = getUserPort;
        this.deleteUsersFromTeamService = deleteUsersFromTeamService;
    }

    @Override
    public void deleteUsersFromTeam(long teamId, List<Long> userIds) {
        if(!getTeamPort.existsById(teamId)){
            throw new TeamNotFoundException(teamId);
        }
        for(Long userId : userIds){
            if(!getUserPort.existsById(userId)) {
                throw new UserNotFoundException(userId);
            }
        }
        deleteUsersFromTeamService.deleteUsersFromTeam(teamId, userIds);
    }
}
