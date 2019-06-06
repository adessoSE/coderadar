package io.reflectoring.coderadar.rest.unit.module;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.projectadministration.port.driver.module.update.UpdateModuleCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.module.update.UpdateModuleUseCase;
import io.reflectoring.coderadar.rest.module.UpdateModuleController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class UpdateModuleControllerTest {

  private UpdateModuleUseCase updateModuleUseCase = mock(UpdateModuleUseCase.class);

  @Test
  void updateModuleWithIdOne() {
    UpdateModuleController testSubject = new UpdateModuleController(updateModuleUseCase);

    UpdateModuleCommand command = new UpdateModuleCommand("new-path");

    ResponseEntity<String> responseEntity = testSubject.updateModule(command, 1L);

    Mockito.verify(updateModuleUseCase, Mockito.times(1)).updateModule(command, 1L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}
