package io.reflectoring.coderadar.useradministration.service.teams.get;

import io.reflectoring.coderadar.useradministration.domain.Team;
import io.reflectoring.coderadar.useradministration.port.driver.teams.get.ListTeamsForProjectUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListTeamsForProjectService implements ListTeamsForProjectUseCase {

    @Override
    public List<Team> listTeamsForProject(long projectId) {
        return null;
    }
}
