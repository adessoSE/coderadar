package io.reflectoring.coderadar.rest.unit.module;

import io.reflectoring.coderadar.core.projectadministration.port.driver.module.get.ListModulesOfProjectUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class ListModulesOfProjectControllerTest {

  @Mock private ListModulesOfProjectUseCase listModulesOfProjectUseCase;
  @InjectMocks private ListModulesOfProjectController testSubject;

  @Test
  void returnsModulesForProjectWithIdOne() {
    Assertions.fail();
  }
}
