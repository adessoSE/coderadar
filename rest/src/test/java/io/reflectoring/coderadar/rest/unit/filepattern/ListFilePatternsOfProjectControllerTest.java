package io.reflectoring.coderadar.rest.unit.filepattern;

import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.get.ListFilePatternsOfProjectUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class ListFilePatternsOfProjectControllerTest {

  @Mock private ListFilePatternsOfProjectUseCase listFilePatternsOfProjectUseCase;
  @InjectMocks private ListFilePatternsOfProjectController testSubject;

  @Test
  public void returnsModulesForProjectWithIdOne() {
    Assertions.fail();
  }
}
