package io.reflectoring.coderadar.projectadministration.module;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import io.reflectoring.coderadar.projectadministration.port.driven.module.ListModulesOfProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.GetModuleResponse;
import io.reflectoring.coderadar.projectadministration.service.module.ListModulesOfProjectService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ListModulesOfProjectServiceTest {

  @Mock private ListModulesOfProjectPort listModulesPortMock;

  private ListModulesOfProjectService testSubject;

  @BeforeEach
  void setUp() {
    this.testSubject = new ListModulesOfProjectService(listModulesPortMock);
  }

  @Test
  void returnsTwoModulesFromProject() {
    // given
    long projectId = 1234L;

    GetModuleResponse expectedResponse1 = new GetModuleResponse(1L, "module-path-one");
    GetModuleResponse expectedResponse2 = new GetModuleResponse(2L, "module-path-two");

    when(listModulesPortMock.listModuleReponses(projectId))
        .thenReturn(Arrays.asList(expectedResponse1, expectedResponse2));

    // when
    List<GetModuleResponse> actualResponses = testSubject.listModules(projectId);

    // then
    assertThat(actualResponses).containsExactly(expectedResponse1, expectedResponse2);
  }
}
