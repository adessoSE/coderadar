package io.reflectoring.coderadar.core.projectadministration.port.driven.project;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;

public interface GetProjectPort {
    Project get(Long id);
}
