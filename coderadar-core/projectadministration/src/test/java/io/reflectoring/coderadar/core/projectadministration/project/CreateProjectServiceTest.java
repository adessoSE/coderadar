package io.reflectoring.coderadar.core.projectadministration.project;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.CreateProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.core.projectadministration.service.project.CreateProjectService;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class CreateProjectServiceTest {
  @Mock private CreateProjectPort createProjectPort;
  private CreateProjectService testSubject;

  @BeforeEach
  void setup() {
    testSubject = new CreateProjectService(createProjectPort);
  }

  @Test
  void returnsNewProjectId() throws MalformedURLException {
    URL url = new URL("http://valid.url");

    Project project = new Project();
    project.setId(1L);
    project.setName("project");
    Mockito.when(createProjectPort.createProject(project)).thenReturn(project);

    CreateProjectCommand command =
        new CreateProjectCommand(
            "project", "username", "password", url, true, new Date(), new Date());
    Long projectId = testSubject.createProject(command);

    Assertions.assertEquals(project.getId(), projectId);
  }
}
