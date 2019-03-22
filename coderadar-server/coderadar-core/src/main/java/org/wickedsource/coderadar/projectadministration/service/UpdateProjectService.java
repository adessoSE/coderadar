package org.wickedsource.coderadar.projectadministration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.projectadministration.port.driven.UpdateProjectPort;
import org.wickedsource.coderadar.projectadministration.port.driver.UpdateProjectCommand;
import org.wickedsource.coderadar.projectadministration.port.driver.UpdateProjectUseCase;

@Service
public class UpdateProjectService implements UpdateProjectUseCase {

    private final UpdateProjectPort updateProjectPort;

    @Autowired
    public UpdateProjectService(UpdateProjectPort updateProjectPort) {
        this.updateProjectPort = updateProjectPort;
    }

    @Override
    public UpdateProjectCommand updateProject(UpdateProjectCommand command) {
        return null;
    }
}
