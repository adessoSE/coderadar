package io.reflectoring.coderadar.core.projectadministration.module;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.port.driven.module.GetModulePort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.module.UpdateModulePort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.update.UpdateModuleCommand;
import io.reflectoring.coderadar.core.projectadministration.service.module.UpdateModuleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class UpdateModuleServiceTest {
  @Mock private GetModulePort getModulePort;
  @Mock private UpdateModulePort updateModulePort;
  private UpdateModuleService testSubject;

  @BeforeEach
  void setup() {
    testSubject = new UpdateModuleService(getModulePort, updateModulePort);
  }

  @Test
  void updateModuleWithIdOne() {
    UpdateModuleCommand command = new UpdateModuleCommand("new-module-path");

    Module module = new Module();
    module.setPath("new-module-path");
    module.setId(1L);

    Mockito.when(getModulePort.get(1L)).thenReturn(module);

    testSubject.updateModule(command, 1L);

    Mockito.verify(updateModulePort, Mockito.times(1)).updateModule(module);
  }
}
