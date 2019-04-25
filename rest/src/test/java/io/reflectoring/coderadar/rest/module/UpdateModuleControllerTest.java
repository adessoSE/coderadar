package io.reflectoring.coderadar.rest.module;

import io.reflectoring.coderadar.core.projectadministration.port.driver.module.UpdateModuleUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class UpdateModuleControllerTest {

  @Mock private UpdateModuleUseCase updateModuleUseCase;
  private UpdateModuleController testSubject;

  @BeforeEach
  public void setup() {
    testSubject = new UpdateModuleController(updateModuleUseCase);
  }

  @Test
  public void updateModuleWithIdOne() {
    // TODO
  }
}
