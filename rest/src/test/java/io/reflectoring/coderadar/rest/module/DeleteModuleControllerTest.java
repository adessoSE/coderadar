package io.reflectoring.coderadar.rest.module;

import io.reflectoring.coderadar.core.projectadministration.port.driver.module.delete.DeleteModuleUseCase;
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
public class DeleteModuleControllerTest {

  @Mock private DeleteModuleUseCase deleteModuleUseCase;
  private DeleteModuleController testSubject;

  @BeforeEach
  public void setup() {
    testSubject = new DeleteModuleController(deleteModuleUseCase);
  }

  @Test
  public void deleteModuleWithIdOne() {
    ResponseEntity<String> responseEntity = testSubject.deleteModule(1L);

    Mockito.verify(deleteModuleUseCase, Mockito.times(1)).delete(1L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}
