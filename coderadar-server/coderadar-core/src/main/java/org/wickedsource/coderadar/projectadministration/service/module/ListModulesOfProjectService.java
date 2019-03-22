package org.wickedsource.coderadar.projectadministration.service.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.projectadministration.port.driven.module.ListModulesOfProjectPort;
import org.wickedsource.coderadar.projectadministration.port.driver.module.ListModulesOfProjectUseCase;

@Service
public class ListModulesOfProjectService implements ListModulesOfProjectUseCase {

    private final ListModulesOfProjectPort listModulesOfProjectPort;

    @Autowired
    public ListModulesOfProjectService(ListModulesOfProjectPort listModulesOfProjectPort) {
        this.listModulesOfProjectPort = listModulesOfProjectPort;
    }
}
