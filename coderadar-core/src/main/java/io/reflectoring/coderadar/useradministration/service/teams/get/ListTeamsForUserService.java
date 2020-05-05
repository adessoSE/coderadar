package io.reflectoring.coderadar.useradministration.service.teams.get;

import io.reflectoring.coderadar.useradministration.domain.Team;
import io.reflectoring.coderadar.useradministration.port.driver.teams.get.ListTeamsForUserUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListTeamsForUserService implements ListTeamsForUserUseCase {
    @Override
    public List<Team> listTeamsForUser(long userId) {
        return null;
    }
}
