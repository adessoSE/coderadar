package io.reflectoring.coderadar.rest.unit.module;

import io.reflectoring.coderadar.core.projectadministration.port.driver.module.update.UpdateModuleCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.update.UpdateModuleUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class UpdateModuleControllerTest {

  @Mock private UpdateModuleUseCase updateModuleUseCase;
  @InjectMocks private UpdateModuleController testSubject;

  @Test
  public void updateModuleWithIdOne() {
    UpdateModuleCommand command = new UpdateModuleCommand("new-path");

    ResponseEntity<String> responseEntity = testSubject.updateModule(command, 1L);

    Mockito.verify(updateModuleUseCase, Mockito.times(1)).updateModule(command, 1L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}
