package io.reflectoring.coderadar.useradministration.port.driven;

import io.reflectoring.coderadar.projectadministration.domain.Project;

import java.util.List;

public interface ListProjectsForUserPort {

    /**
     * @param userId The id of the user.
     * @return All projects this user has access to.
     */
    List<Project> listProjects(long userId);
}
