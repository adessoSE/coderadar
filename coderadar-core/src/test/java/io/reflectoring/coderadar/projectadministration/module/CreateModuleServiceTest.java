package io.reflectoring.coderadar.projectadministration.module;

import io.reflectoring.coderadar.projectadministration.ModuleAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ModulePathInvalidException;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.module.CreateModulePort;
import io.reflectoring.coderadar.projectadministration.port.driven.module.SaveModulePort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleCommand;
import io.reflectoring.coderadar.projectadministration.service.ProcessProjectService;
import io.reflectoring.coderadar.projectadministration.service.module.CreateModuleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateModuleServiceTest {

  @Mock private CreateModulePort createModulePortMock;

  @Mock private SaveModulePort saveModulePortMock;

  @Mock private ProcessProjectService processProjectServiceMock;

  private CreateModuleService testSubject;

  @BeforeEach
  void setUp() {
    this.testSubject = new CreateModuleService(createModulePortMock, saveModulePortMock, processProjectServiceMock);
  }

  @Test
  void returnsNewModuleId() throws ModulePathInvalidException, ModuleAlreadyExistsException, ProjectIsBeingProcessedException {
    // given
    long projectId = 1L;
    long expectedModuleId = 123L;

    Module expectedModule = new Module()
            .setPath("module-path");

    CreateModuleCommand command = new CreateModuleCommand("module-path");

    when(saveModulePortMock.saveModule(expectedModule, projectId)).thenReturn(expectedModuleId);
    when(processProjectServiceMock.executeTask(any(), anyLong())).thenAnswer((Answer<Void>) invocation -> {
      Runnable runnable = invocation.getArgument(0);
      runnable.run();
      return null;
    });

    // when
    Long actualModuleId = testSubject.createModule(command, projectId);

    // then
    assertThat(actualModuleId).isEqualTo(expectedModuleId);

    verify(createModulePortMock).createModule(expectedModuleId, projectId);
  }
}
