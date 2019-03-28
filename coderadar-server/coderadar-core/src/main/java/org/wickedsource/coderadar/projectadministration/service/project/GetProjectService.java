package org.wickedsource.coderadar.projectadministration.service.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.projectadministration.domain.Project;
import org.wickedsource.coderadar.projectadministration.port.driven.project.GetProjectPort;
import org.wickedsource.coderadar.projectadministration.port.driver.project.GetProjectCommand;
import org.wickedsource.coderadar.projectadministration.port.driver.project.GetProjectUseCase;

@Service
public class GetProjectService implements GetProjectUseCase {

    private final GetProjectPort port;

    public GetProjectService(GetProjectPort port) {
        this.port = port;
    }

    @Override
    public Project get(GetProjectCommand command) {
        return port.get(command.getId());
    }
}
