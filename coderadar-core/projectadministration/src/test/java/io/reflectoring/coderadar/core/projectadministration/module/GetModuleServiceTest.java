package io.reflectoring.coderadar.core.projectadministration.module;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.port.driven.module.GetModulePort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.get.GetModuleResponse;
import io.reflectoring.coderadar.core.projectadministration.service.module.GetModuleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class GetModuleServiceTest {
  @Mock private GetModulePort getModulePort;
  @InjectMocks private GetModuleService testSubject;

  @Test
  void returnsGetModuleResponseForModule() {
    Module module = new Module();
    module.setId(1L);
    module.setPath("module-path");
    Mockito.when(getModulePort.get(1L)).thenReturn(module);

    GetModuleResponse response = testSubject.get(1L);

    Assertions.assertEquals(module.getId(), response.getId());
    Assertions.assertEquals(module.getPath(), response.getPath());
  }
}
