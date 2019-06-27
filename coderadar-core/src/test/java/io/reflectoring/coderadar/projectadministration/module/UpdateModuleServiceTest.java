package io.reflectoring.coderadar.projectadministration.module;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.GetModulePort;
import io.reflectoring.coderadar.projectadministration.port.driven.module.UpdateModulePort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.update.UpdateModuleCommand;
import io.reflectoring.coderadar.projectadministration.service.module.UpdateModuleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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

    Mockito.when(getModulePort.get(1L)).thenReturn(module);

    testSubject.updateModule(command, 1L);

    Mockito.verify(updateModulePort, Mockito.times(1)).updateModule(module);
  }
}
