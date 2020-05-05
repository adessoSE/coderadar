package io.reflectoring.coderadar.useradministration.service.teams.create;

import io.reflectoring.coderadar.useradministration.port.driver.teams.create.CreateTeamForProjectCommand;
import io.reflectoring.coderadar.useradministration.port.driver.teams.create.CreateTeamForProjectUseCase;
import org.springframework.stereotype.Service;

@Service
public class CreateTeamForProjectService implements CreateTeamForProjectUseCase {
    @Override
    public Long createTeamForProject(long projectId, CreateTeamForProjectCommand command) {
        return null;
    }
}
