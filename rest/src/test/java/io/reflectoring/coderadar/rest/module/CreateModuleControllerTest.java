package io.reflectoring.coderadar.rest.module;

import io.reflectoring.coderadar.core.projectadministration.port.driver.module.CreateModuleCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.CreateModuleUseCase;
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
    CreateModuleCommand command = new CreateModuleCommand(5L, "module-path-test");
    Mockito.when(createModuleUseCase.createModule(command)).thenReturn(1L);

    ResponseEntity<Long> responseEntity = testSubject.createModule(command);

    Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    Assertions.assertEquals(1L, responseEntity.getBody().longValue());
  }
}
