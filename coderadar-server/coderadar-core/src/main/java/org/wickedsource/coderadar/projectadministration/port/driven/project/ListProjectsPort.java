package org.wickedsource.coderadar.projectadministration.port.driven.project;

import org.wickedsource.coderadar.projectadministration.domain.Project;

import java.util.List;

public interface ListProjectsPort {
    List<Project> getProjects();
}
