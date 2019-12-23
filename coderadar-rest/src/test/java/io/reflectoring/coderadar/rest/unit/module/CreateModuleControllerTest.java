package io.reflectoring.coderadar.rest.unit.module;

import io.reflectoring.coderadar.projectadministration.ModuleAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ModulePathInvalidException;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleUseCase;
import io.reflectoring.coderadar.rest.domain.IdResponse;
import io.reflectoring.coderadar.rest.module.CreateModuleController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;

class CreateModuleControllerTest {

  private CreateModuleUseCase createModuleUseCase = mock(CreateModuleUseCase.class);

  @Test
  void createModuleSuccessfully() throws ModuleAlreadyExistsException, ModulePathInvalidException, ProjectIsBeingProcessedException {
    CreateModuleController testSubject = new CreateModuleController(createModuleUseCase);

    CreateModuleCommand command = new CreateModuleCommand("module-path-test");
    Mockito.when(createModuleUseCase.createModule(command, 5L)).thenReturn(1L);

    ResponseEntity<IdResponse> responseEntity = testSubject.createModule(command, 5L);

    Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    Assertions.assertEquals(1L, responseEntity.getBody().getId().longValue());
  }
}
