package io.reflectoring.coderadar.core.projectadministration.service.module;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.port.driven.module.GetModulePort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.module.UpdateModulePort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.UpdateModuleCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.UpdateModuleUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateModuleService implements UpdateModuleUseCase {

    private final GetModulePort getModulePort;
    private final UpdateModulePort updateModulePort;

    @Autowired
    public UpdateModuleService(GetModulePort getModulePort, UpdateModulePort updateModulePort) {
        this.getModulePort = getModulePort;
        this.updateModulePort = updateModulePort;
    }

    @Override
    public Module updateModule(UpdateModuleCommand command) {
        Module module = getModulePort.get(command.getId());
        module.setPath(command.getPath());
        module = updateModulePort.updateModule(module);
        return module;
    }
}
