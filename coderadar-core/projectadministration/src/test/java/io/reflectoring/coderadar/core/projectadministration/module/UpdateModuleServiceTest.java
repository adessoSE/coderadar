package io.reflectoring.coderadar.core.projectadministration.module;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.port.driven.module.GetModulePort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.module.UpdateModulePort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.update.UpdateModuleCommand;
import io.reflectoring.coderadar.core.projectadministration.service.module.UpdateModuleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.mock;

class UpdateModuleServiceTest {
  private GetModulePort getModulePort = mock(GetModulePort.class);
  private UpdateModulePort updateModulePort = mock(UpdateModulePort.class);

  @Test
  void updateModuleWithIdOne() {
    UpdateModuleService testSubject = new UpdateModuleService(getModulePort, updateModulePort);

    UpdateModuleCommand command = new UpdateModuleCommand("new-module-path");

    Module module = new Module();
    module.setPath("new-module-path");
    module.setId(1L);

    Mockito.when(getModulePort.get(1L)).thenReturn(Optional.of(module));

    testSubject.updateModule(command, 1L);

    Mockito.verify(updateModulePort, Mockito.times(1)).updateModule(module);
  }
}
