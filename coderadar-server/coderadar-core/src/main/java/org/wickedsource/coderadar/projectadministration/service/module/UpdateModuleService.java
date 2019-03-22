package org.wickedsource.coderadar.projectadministration.service.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.projectadministration.port.driven.module.UpdateModulePort;
import org.wickedsource.coderadar.projectadministration.port.driver.module.UpdateModuleUseCase;

@Service
public class UpdateModuleService implements UpdateModuleUseCase {

    private final UpdateModulePort updateModulePort;

    @Autowired
    public UpdateModuleService(UpdateModulePort updateModulePort) {
        this.updateModulePort = updateModulePort;
    }
}
