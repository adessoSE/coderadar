package org.wickedsource.coderadar.projectadministration.service.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.projectadministration.port.driven.project.ListProjectsPort;
import org.wickedsource.coderadar.projectadministration.port.driver.project.ListProjectsUseCase;

@Service
public class ListProjectsService implements ListProjectsUseCase {

    private final ListProjectsPort listProjectsPort;

    @Autowired
    public ListProjectsService(ListProjectsPort listProjectsPort) {
        this.listProjectsPort = listProjectsPort;
    }
}
