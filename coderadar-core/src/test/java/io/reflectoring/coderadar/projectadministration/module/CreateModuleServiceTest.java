package io.reflectoring.coderadar.projectadministration.module;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.projectadministration.ModuleAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ModulePathInvalidException;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.module.CreateModulePort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleCommand;
import io.reflectoring.coderadar.projectadministration.service.module.CreateModuleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CreateModuleServiceTest {
  private CreateModulePort createModulePort = mock(CreateModulePort.class);
  private GetProjectPort getProjectPort = mock(GetProjectPort.class);

  @Test
  void returnsNewModuleId()
      throws ModulePathInvalidException, ModuleAlreadyExistsException,
          ProjectIsBeingProcessedException {
    CreateModuleService testSubject = new CreateModuleService(createModulePort);

    Project project = new Project();
    project.setId(2L);
    project.setName("project name");

    Mockito.when(getProjectPort.get(2L)).thenReturn(project);

    Module module = new Module();
    module.setPath("module-path");
    Mockito.when(createModulePort.createModule(module, 2L)).thenReturn(1L);

    CreateModuleCommand command = new CreateModuleCommand("module-path");
    Long moduleId = testSubject.createModule(command, project.getId());

    Assertions.assertEquals(1L, moduleId.longValue());
  }
}
