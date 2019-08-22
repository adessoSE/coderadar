package io.reflectoring.coderadar.projectadministration.module;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.module.ListModulesOfProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.GetModuleResponse;
import io.reflectoring.coderadar.projectadministration.service.module.ListModulesOfProjectService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
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

    Module module1 = new Module()
            .setId(1L)
            .setPath("module-path-one");
    Module module2 = new Module()
            .setId(2L)
            .setPath("module-path-two");

    List<Module> modules = new ArrayList<>();
    modules.add(module1);
    modules.add(module2);

    GetModuleResponse expectedResponse1 = new GetModuleResponse(1L, "module-path-one");
    GetModuleResponse expectedResponse2 = new GetModuleResponse(2L, "module-path-two");

    when(listModulesPortMock.listModules(projectId)).thenReturn(modules);

    // when
    List<GetModuleResponse> actualResponses = testSubject.listModules(projectId);

    // then
    assertThat(actualResponses).containsExactly(expectedResponse1, expectedResponse2);
  }
}
