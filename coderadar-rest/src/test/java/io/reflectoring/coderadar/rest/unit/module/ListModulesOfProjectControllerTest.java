package io.reflectoring.coderadar.rest.unit.module;

import io.reflectoring.coderadar.projectadministration.port.driver.module.get.GetModuleResponse;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.ListModulesOfProjectUseCase;
import io.reflectoring.coderadar.rest.module.ListModulesOfProjectController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

class ListModulesOfProjectControllerTest {

  private ListModulesOfProjectUseCase listModulesOfProjectUseCase =
      mock(ListModulesOfProjectUseCase.class);

  @Test
  void returnsModulesForProjectWithIdOne() {
    ListModulesOfProjectController testSubject =
        new ListModulesOfProjectController(listModulesOfProjectUseCase);

    List<GetModuleResponse> responses = new ArrayList<>();
    GetModuleResponse response1 = new GetModuleResponse(1L, "module-path-one");
    GetModuleResponse response2 = new GetModuleResponse(2L, "module-path-two");
    responses.add(response1);
    responses.add(response2);

    Mockito.when(listModulesOfProjectUseCase.listModules(1L)).thenReturn(responses);

    ResponseEntity<List<GetModuleResponse>> responseEntity = testSubject.listModules(1L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertEquals(responses.size(), responseEntity.getBody().size());
    Assertions.assertEquals(response1.getId(), responseEntity.getBody().get(0).getId());
    Assertions.assertEquals(response1.getPath(), responseEntity.getBody().get(0).getPath());
    Assertions.assertEquals(response2.getId(), responseEntity.getBody().get(1).getId());
    Assertions.assertEquals(response2.getPath(), responseEntity.getBody().get(1).getPath());
  }
}
