package io.reflectoring.coderadar.projectadministration.module;

import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.GetModulePort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.GetModuleResponse;
import io.reflectoring.coderadar.projectadministration.service.module.GetModuleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetModuleServiceTest {

  @Mock private GetModulePort getModulePortMock;

  private GetModuleService testSubject;

  @BeforeEach
  void setUp() {
    this.testSubject = new GetModuleService(getModulePortMock);
  }

  @Test
  void returnsGetModuleResponseForModule() {
    // given
    long moduleId = 1L;
    String path = "module-path";

    Module module = new Module()
            .setId(moduleId)
            .setPath(path);

    GetModuleResponse expectedResponse = new GetModuleResponse(moduleId, path);

    when(getModulePortMock.get(moduleId)).thenReturn(module);

    // when
    GetModuleResponse actualResponse = testSubject.get(moduleId);

    // then
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }
}
