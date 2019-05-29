package io.reflectoring.coderadar.rest.unit.module;

import io.reflectoring.coderadar.core.projectadministration.port.driver.module.delete.DeleteModuleUseCase;
import io.reflectoring.coderadar.rest.module.DeleteModuleController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;

class DeleteModuleControllerTest {

  private DeleteModuleUseCase deleteModuleUseCase = mock(DeleteModuleUseCase.class);

  @Test
  void deleteModuleWithIdOne() {
    DeleteModuleController testSubject = new DeleteModuleController(deleteModuleUseCase);

    ResponseEntity<String> responseEntity = testSubject.deleteModule(1L);

    Mockito.verify(deleteModuleUseCase, Mockito.times(1)).delete(1L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}
