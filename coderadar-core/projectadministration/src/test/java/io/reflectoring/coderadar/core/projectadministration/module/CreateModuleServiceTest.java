package io.reflectoring.coderadar.core.projectadministration.module;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.module.CreateModulePort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.create.CreateModuleCommand;
import io.reflectoring.coderadar.core.projectadministration.service.module.CreateModuleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.mock;

class CreateModuleServiceTest {
  private CreateModulePort createModulePort = mock(CreateModulePort.class);
  private GetProjectPort getProjectPort = mock(GetProjectPort.class);

  @Test
  void returnsNewModuleId() {
    CreateModuleService testSubject = new CreateModuleService(getProjectPort, createModulePort);

    Project project = new Project();
    project.setId(2L);
    project.setName("project name");

    Mockito.when(getProjectPort.get(2L)).thenReturn(Optional.of(project));

    Module module = new Module();
    module.setProject(project);
    module.setPath("module-path");
    Mockito.when(createModulePort.createModule(module)).thenReturn(1L);

    CreateModuleCommand command = new CreateModuleCommand("module-path");
    Long moduleId = testSubject.createModule(command, project.getId());

    Assertions.assertEquals(1L, moduleId.longValue());
  }
}
