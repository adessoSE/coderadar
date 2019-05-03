package io.reflectoring.coderadar.core.projectadministration.module;

import io.reflectoring.coderadar.core.projectadministration.port.driven.module.DeleteModulePort;
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
  @InjectMocks private DeleteModuleService testSubject;

  @Test
  void deleteModuleWithIdOne() {
    testSubject.delete(1L);

    Mockito.verify(deleteModulePort, Mockito.times(1)).delete(1L);
  }
}
