package io.reflectoring.coderadar.core.projectadministration.module;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.port.driven.module.DeleteModulePort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.module.GetModulePort;
import io.reflectoring.coderadar.core.projectadministration.service.module.DeleteModuleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;

class DeleteModuleServiceTest {
  private DeleteModulePort deleteModulePort = mock(DeleteModulePort.class);
  private GetModulePort getModulePort = mock(GetModulePort.class);

  @Test
  void deleteModuleWithIdOne() {
    DeleteModuleService testSubject = new DeleteModuleService(deleteModulePort, getModulePort);

    Mockito.when(getModulePort.get(anyLong())).thenReturn(java.util.Optional.of(new Module()));
    testSubject.delete(1L);

    Mockito.verify(deleteModulePort, Mockito.times(1)).delete(1L);
  }
}
