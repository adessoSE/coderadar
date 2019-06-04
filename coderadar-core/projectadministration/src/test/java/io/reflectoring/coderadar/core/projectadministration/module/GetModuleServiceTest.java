package io.reflectoring.coderadar.core.projectadministration.module;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.port.driven.module.GetModulePort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.get.GetModuleResponse;
import io.reflectoring.coderadar.core.projectadministration.service.module.GetModuleService;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GetModuleServiceTest {
  private GetModulePort getModulePort = mock(GetModulePort.class);

  @Test
  void returnsGetModuleResponseForModule() {
    GetModuleService testSubject = new GetModuleService(getModulePort);

    Module module = new Module();
    module.setId(1L);
    module.setPath("module-path");
    Mockito.when(getModulePort.get(1L)).thenReturn(Optional.of(module));

    GetModuleResponse response = testSubject.get(1L);

    Assertions.assertEquals(module.getId(), response.getId());
    Assertions.assertEquals(module.getPath(), response.getPath());
  }
}
