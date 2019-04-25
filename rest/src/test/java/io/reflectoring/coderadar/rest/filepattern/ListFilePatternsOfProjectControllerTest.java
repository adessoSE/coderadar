package io.reflectoring.coderadar.rest.filepattern;

import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.get.ListFilePatternsOfProjectUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class ListFilePatternsOfProjectControllerTest {

  @Mock private ListFilePatternsOfProjectUseCase listFilePatternsOfProjectUseCase;
  private ListFilePatternsOfProjectController testSubject;

  @BeforeEach
  public void setup() {
    testSubject = new ListFilePatternsOfProjectController(listFilePatternsOfProjectUseCase);
  }

  @Test
  public void returnsModulesForProjectWithIdOne() {
    // TODO
  }
}
