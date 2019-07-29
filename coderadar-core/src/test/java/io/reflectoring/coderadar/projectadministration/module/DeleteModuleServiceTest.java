package io.reflectoring.coderadar.projectadministration.module;

import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.DeleteModulePort;
import io.reflectoring.coderadar.projectadministration.port.driven.module.GetModulePort;
import io.reflectoring.coderadar.projectadministration.service.ProcessProjectService;
import io.reflectoring.coderadar.projectadministration.service.module.DeleteModuleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;

class DeleteModuleServiceTest {
  private DeleteModulePort deleteModulePort = mock(DeleteModulePort.class);
  private GetModulePort getModulePort = mock(GetModulePort.class);
  private ProcessProjectService processProjectService = mock(ProcessProjectService.class);

  @Test
  void deleteModuleWithIdOne() throws ProjectIsBeingProcessedException {
    DeleteModuleService testSubject =
        new DeleteModuleService(deleteModulePort, processProjectService, getModulePort);

    Mockito.when(getModulePort.get(anyLong())).thenReturn(new Module());
    testSubject.delete(1L, 2L);

    //Mockito.verify(deleteModulePort, Mockito.times(1)).delete(1L, 2L);
  }
}
