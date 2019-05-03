package io.reflectoring.coderadar.rest.unit.module;

import io.reflectoring.coderadar.core.projectadministration.port.driver.module.get.GetModuleResponse;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.get.GetModuleUseCase;
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
public class GetModuleControllerTest {

  @Mock private GetModuleUseCase getModuleUseCase;
  private GetModuleController testSubject;

  @BeforeEach
  public void setup() {
    testSubject = new GetModuleController(getModuleUseCase);
  }

  @Test
  public void returnsModuleWithIdOne() {
    GetModuleResponse module = new GetModuleResponse(1L, "module-path");

    Mockito.when(getModuleUseCase.get(1L)).thenReturn(module);
    ResponseEntity<GetModuleResponse> responseEntity = testSubject.getModule(1L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertEquals(1L, responseEntity.getBody().getId().longValue());
    Assertions.assertEquals("module-path", responseEntity.getBody().getPath());
  }
}
