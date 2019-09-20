package io.reflectoring.coderadar.projectadministration.module;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.reflectoring.coderadar.projectadministration.ModuleAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ModulePathInvalidException;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.CreateModulePort;
import io.reflectoring.coderadar.projectadministration.port.driven.module.SaveModulePort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ProjectStatusPort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleCommand;
import io.reflectoring.coderadar.projectadministration.service.module.CreateModuleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateModuleServiceTest {

  @Mock private CreateModulePort createModulePortMock;

  @Mock private SaveModulePort saveModulePortMock;

  @Mock private ProjectStatusPort projectStatusPort;

  private CreateModuleService testSubject;

  @BeforeEach
  void setUp() {
    this.testSubject =
        new CreateModuleService(createModulePortMock, saveModulePortMock, projectStatusPort);
  }

  @Test
  void returnsNewModuleId()
      throws ModulePathInvalidException, ModuleAlreadyExistsException,
          ProjectIsBeingProcessedException {
    // given
    long projectId = 1L;
    long expectedModuleId = 123L;

    Module expectedModule = new Module().setPath("module-path");

    CreateModuleCommand command = new CreateModuleCommand("module-path");

    when(saveModulePortMock.saveModule(expectedModule, projectId)).thenReturn(expectedModuleId);

    // when
    Long actualModuleId = testSubject.createModule(command, projectId);

    // then
    assertThat(actualModuleId).isEqualTo(expectedModuleId);

    verify(createModulePortMock).createModule(expectedModuleId, projectId);
  }
}
