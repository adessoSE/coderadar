package io.reflectoring.coderadar.core.projectadministration.module;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.module.CreateModulePort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.create.CreateModuleCommand;
import io.reflectoring.coderadar.core.projectadministration.service.module.CreateModuleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class CreateModuleServiceTest {
  @Mock private CreateModulePort createModulePort;
  @Mock private GetProjectPort getProjectPort;
  private CreateModuleService testSubject;

  @BeforeEach
  void setup() {
    testSubject = new CreateModuleService(createModulePort, getProjectPort);
  }

  @Test
  void returnsNewModuleId() {
    Project project = new Project();
    project.setId(2L);
    project.setName("project name");

    Mockito.when(getProjectPort.get(2L)).thenReturn(project);

    Module module = new Module();
    module.setId(1L);
    module.setProject(project);
    module.setPath("module-path");
    Mockito.when(createModulePort.createModule(module)).thenReturn(module);

    CreateModuleCommand command = new CreateModuleCommand("module-path");
    Long moduleId = testSubject.createModule(command, project.getId());

    Assertions.assertEquals(module.getId(), moduleId);
  }
}
