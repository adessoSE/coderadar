package io.reflectoring.coderadar.core.projectadministration.port.driven.project;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;

public interface DeleteProjectPort {
    void delete(Long id);

    void delete(Project project);
}
