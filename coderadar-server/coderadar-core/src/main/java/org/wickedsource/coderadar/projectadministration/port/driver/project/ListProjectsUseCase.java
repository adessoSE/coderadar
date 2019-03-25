package org.wickedsource.coderadar.projectadministration.port.driver.project;

import org.wickedsource.coderadar.projectadministration.domain.Project;

import java.util.List;

public interface ListProjectsUseCase {
    List<Project> listProjects();
}
