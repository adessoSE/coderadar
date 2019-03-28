package org.wickedsource.coderadar.projectadministration.port.driven.project;

import org.wickedsource.coderadar.projectadministration.domain.Project;

public interface GetProjectPort {
    Project get(Long id);
}
