package io.reflectoring.coderadar.core.projectadministration.module;

import static org.mockito.ArgumentMatchers.anyLong;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.port.driven.module.DeleteModulePort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.module.GetModulePort;
import io.reflectoring.coderadar.core.projectadministration.service.module.DeleteModuleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class DeleteModuleServiceTest {
  @Mock private DeleteModulePort deleteModulePort;
  @Mock private GetModulePort getModulePort;
  @InjectMocks private DeleteModuleService testSubject;

  @Test
  void deleteModuleWithIdOne() {
    Mockito.when(getModulePort.get(anyLong())).thenReturn(java.util.Optional.of(new Module()));
    testSubject.delete(1L);

    Mockito.verify(deleteModulePort, Mockito.times(1)).delete(1L);
  }
}
