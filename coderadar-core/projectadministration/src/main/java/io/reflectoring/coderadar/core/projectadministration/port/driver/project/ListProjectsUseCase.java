package io.reflectoring.coderadar.core.projectadministration.port.driver.project;


import io.reflectoring.coderadar.core.projectadministration.domain.Project;

import java.util.List;

public interface ListProjectsUseCase {
    List<Project> listProjects();
}
