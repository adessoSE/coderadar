package org.wickedsource.coderadar.projectadministration.service.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.projectadministration.domain.Module;
import org.wickedsource.coderadar.projectadministration.port.driven.module.GetModulePort;
import org.wickedsource.coderadar.projectadministration.port.driver.module.GetModuleCommand;
import org.wickedsource.coderadar.projectadministration.port.driver.module.GetModuleUseCase;

@Service
public class GetModuleService implements GetModuleUseCase {
    private final GetModulePort getModulePort;

    @Autowired
    public GetModuleService(GetModulePort getModulePort) {
        this.getModulePort = getModulePort;
    }

    @Override
    public Module get(GetModuleCommand command) {
        return getModulePort.get(command.getId());
    }
}
