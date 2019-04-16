package io.reflectoring.coderadar.core.projectadministration.service.module;

import io.reflectoring.coderadar.core.projectadministration.port.driven.module.DeleteModulePort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.DeleteModuleCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.DeleteModuleUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteModuleService implements DeleteModuleUseCase {

    private final DeleteModulePort deleteModulePort;

    @Autowired
    public DeleteModuleService(DeleteModulePort deleteModulePort) {
        this.deleteModulePort = deleteModulePort;
    }

    @Override
    public void delete(DeleteModuleCommand command) {
        deleteModulePort.delete(command.getId());
    }
}
