package io.reflectoring.coderadar.core.projectadministration.module;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.module.ListModulesOfProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.get.GetModuleResponse;
import io.reflectoring.coderadar.core.projectadministration.service.module.ListModulesOfProjectService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ListModulesOfProjectServiceTest {
  @Mock private ListModulesOfProjectPort port;
  @InjectMocks private ListModulesOfProjectService testSubject;

  @Test
  void returnsTwoModulesFromProject() {
    Project project = new Project();
    project.setId(1L);

    List<Module> modules = new ArrayList<>();
    Module module1 = new Module();
    module1.setId(1L);
    module1.setPath("module-path-one");
    module1.setProject(project);
    Module module2 = new Module();
    module2.setId(2L);
    module2.setPath("module-path-two");
    module2.setProject(project);

    modules.add(module1);
    modules.add(module2);

    Mockito.when(port.listModules(project.getId())).thenReturn(modules);

    List<GetModuleResponse> response = testSubject.listModules(project.getId());

    Assertions.assertEquals(modules.size(), response.size());
    Assertions.assertEquals(module1.getId(), response.get(0).getId());
    Assertions.assertEquals(module1.getPath(), response.get(0).getPath());
    Assertions.assertEquals(module2.getId(), response.get(1).getId());
    Assertions.assertEquals(module2.getPath(), response.get(1).getPath());
  }
}
