package io.reflectoring.coderadar.rest.module;

import io.reflectoring.coderadar.core.projectadministration.port.driver.module.delete.DeleteModuleUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class DeleteModuleControllerTest {

  @Mock private DeleteModuleUseCase deleteModuleUseCase;
  private DeleteModuleController testSubject;

  @BeforeEach
  public void setup() {
    testSubject = new DeleteModuleController(deleteModuleUseCase);
  }

  @Test
  public void deleteModuleWithIdOne() {
    // TODO
  }
}
