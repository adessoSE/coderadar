package io.reflectoring.coderadar.useradministration.service.get;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.useradministration.port.driver.get.ListProjectsForUserUseCase;

import java.util.List;

public class ListProjectsForUserService implements ListProjectsForUserUseCase {
    @Override
    public List<Project> listProjects(long userId) {
        return null;
    }
}
