package io.reflectoring.coderadar.rest.unit.module;

import io.reflectoring.coderadar.core.projectadministration.port.driver.module.create.CreateModuleCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.create.CreateModuleUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class CreateModuleControllerTest {

  @Mock private CreateModuleUseCase createModuleUseCase;
  private CreateModuleController testSubject;

  @BeforeEach
  public void setup() {
    testSubject = new CreateModuleController(createModuleUseCase);
  }

  @Test
  public void createModuleSuccessfully() {
    CreateModuleCommand command = new CreateModuleCommand("module-path-test");
    Mockito.when(createModuleUseCase.createModule(command, 5L)).thenReturn(1L);

    ResponseEntity<Long> responseEntity = testSubject.createModule(command, 5L);

    Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    Assertions.assertEquals(1L, responseEntity.getBody().longValue());
  }
}
