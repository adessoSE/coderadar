package io.reflectoring.coderadar.core.projectadministration.service.module;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.port.driven.module.ListModulesOfProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.ListModulesOfProjectCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.ListModulesOfProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListModulesOfProjectService implements ListModulesOfProjectUseCase {

    private final ListModulesOfProjectPort port;

    @Autowired
    public ListModulesOfProjectService(ListModulesOfProjectPort port) {
        this.port = port;
    }

    @Override
    public List<Module> listModules(ListModulesOfProjectCommand command) {
        return port.listModules(command.getProjectId());
    }
}
