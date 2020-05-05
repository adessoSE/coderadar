package io.reflectoring.coderadar.useradministration.port.driver.get;

import io.reflectoring.coderadar.projectadministration.domain.Project;

import java.util.List;

public interface ListProjectsForUserUseCase {

    /**
     * @param userId The id of the user.
     * @return All projects this user has access to.
     */
    List<Project> listProjects(long userId);
}
