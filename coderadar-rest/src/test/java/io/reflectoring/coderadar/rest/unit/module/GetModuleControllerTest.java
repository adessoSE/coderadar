package io.reflectoring.coderadar.rest.unit.module;

import io.reflectoring.coderadar.projectadministration.port.driver.module.get.GetModuleResponse;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.GetModuleUseCase;
import io.reflectoring.coderadar.rest.module.GetModuleController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;

class GetModuleControllerTest {

  private GetModuleUseCase getModuleUseCase = mock(GetModuleUseCase.class);

  @Test
  void returnsModuleWithIdOne() {
    GetModuleController testSubject = new GetModuleController(getModuleUseCase);

    GetModuleResponse module = new GetModuleResponse(1L, "module-path");

    Mockito.when(getModuleUseCase.get(1L)).thenReturn(module);
    ResponseEntity<GetModuleResponse> responseEntity = testSubject.getModule(1L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertEquals(1L, responseEntity.getBody().getId().longValue());
    Assertions.assertEquals("module-path", responseEntity.getBody().getPath());
  }
}
