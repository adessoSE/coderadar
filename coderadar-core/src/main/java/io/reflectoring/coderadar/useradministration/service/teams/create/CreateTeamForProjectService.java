package io.reflectoring.coderadar.useradministration.service.teams.create;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.useradministration.port.driven.CreateTeamForProjectPort;
import io.reflectoring.coderadar.useradministration.port.driver.teams.create.CreateTeamForProjectCommand;
import io.reflectoring.coderadar.useradministration.port.driver.teams.create.CreateTeamForProjectUseCase;
import org.springframework.stereotype.Service;

@Service
public class CreateTeamForProjectService implements CreateTeamForProjectUseCase {

    private final GetProjectPort getProjectPort;
    private final CreateTeamForProjectPort createTeamForProjectPort;

    public CreateTeamForProjectService(GetProjectPort getProjectPort, CreateTeamForProjectPort createTeamForProjectPort) {
        this.getProjectPort = getProjectPort;
        this.createTeamForProjectPort = createTeamForProjectPort;
    }

    @Override
    public Long createTeamForProject(long projectId, CreateTeamForProjectCommand command) {
        if(!getProjectPort.existsById(projectId)){
            throw new ProjectNotFoundException(projectId);
        }
        return createTeamForProjectPort.createTeamForProject(projectId, command);
    }
}
