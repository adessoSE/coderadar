package io.reflectoring.coderadar.rest.unit.module;

import io.reflectoring.coderadar.projectadministration.ModulePathInvalidException;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleUseCase;
import io.reflectoring.coderadar.rest.domain.ErrorMessageResponse;
import io.reflectoring.coderadar.rest.domain.IdResponse;
import io.reflectoring.coderadar.rest.module.CreateModuleController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;

class CreateModuleControllerTest {

  private final CreateModuleUseCase createModuleUseCase = mock(CreateModuleUseCase.class);

  @Test
  void testCreateModule() throws ModulePathInvalidException {
    CreateModuleController testSubject = new CreateModuleController(createModuleUseCase);

    CreateModuleCommand command = new CreateModuleCommand("module-path-test");
    Mockito.when(createModuleUseCase.createModule(command, 5L)).thenReturn(1L);

    ResponseEntity<Object> responseEntity = testSubject.createModule(command, 5L);

    Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    Assertions.assertNotNull(responseEntity.getBody());
    Assertions.assertTrue(responseEntity.getBody() instanceof IdResponse);
    Assertions.assertEquals(1L, ((IdResponse) responseEntity.getBody()).getId());
  }

  @Test
  void testCreateModuleReturnsErrorWhenPathInvalid() throws ModulePathInvalidException {
    CreateModuleController testSubject = new CreateModuleController(createModuleUseCase);

    CreateModuleCommand command = new CreateModuleCommand("module-path-test//");
    Mockito.when(createModuleUseCase.createModule(command, 5L))
        .thenThrow(new ModulePathInvalidException(command.getPath()));

    ResponseEntity<Object> responseEntity = testSubject.createModule(command, 5L);

    Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    Assertions.assertNotNull(responseEntity.getBody());
    Assertions.assertTrue(responseEntity.getBody() instanceof ErrorMessageResponse);
    Assertions.assertEquals(
        "module-path-test// is not a valid path!",
        ((ErrorMessageResponse) responseEntity.getBody()).getErrorMessage());
  }
}
