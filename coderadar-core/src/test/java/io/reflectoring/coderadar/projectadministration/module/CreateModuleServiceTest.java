package io.reflectoring.coderadar.projectadministration.module;

import io.reflectoring.coderadar.projectadministration.ModuleAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ModulePathInvalidException;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.port.driven.module.CreateModulePort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ProjectStatusPort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleCommand;
import io.reflectoring.coderadar.projectadministration.service.module.CreateModuleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateModuleServiceTest {

  @Mock private CreateModulePort createModulePortMock;

  @Mock private ProjectStatusPort projectStatusPort;

  @Mock private GetProjectPort getProjectPort;

  private CreateModuleService testSubject;

  @BeforeEach
  void setUp() {
    this.testSubject =
        new CreateModuleService(createModulePortMock, projectStatusPort, getProjectPort);
  }

  @Test
  void returnsNewModuleId()
      throws ModulePathInvalidException, ModuleAlreadyExistsException,
          ProjectIsBeingProcessedException {
    // given
    long projectId = 1L;
    long expectedModuleId = 123L;

    CreateModuleCommand command = new CreateModuleCommand("module-path");

    when(getProjectPort.existsById(anyLong())).thenReturn(true);
    when(createModulePortMock.createModule(command.getPath(), projectId))
        .thenReturn(expectedModuleId);

    // when
    Long actualModuleId = testSubject.createModule(command, projectId);

    // then
    assertThat(actualModuleId).isEqualTo(expectedModuleId);

    verify(createModulePortMock).createModule(command.getPath(), projectId);
  }
}
