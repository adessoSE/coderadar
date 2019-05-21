package io.reflectoring.coderadar.core.projectadministration.project;

import static org.mockito.ArgumentMatchers.any;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.CreateProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.core.projectadministration.service.project.CreateProjectService;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class CreateProjectServiceTest {
  @Mock private CreateProjectPort createProjectPort;
  @InjectMocks private CreateProjectService testSubject;

  @Test
  void returnsNewProjectId() {
    CreateProjectCommand command =
        new CreateProjectCommand(
            "project", "username", "password", "http://valid.url", true, new Date(), new Date());

    Project project = new Project();
    project.setName("project");
    project.setVcsUrl("http://valid.url");
    project.setVcsUsername("username");
    project.setVcsPassword("password");
    project.setVcsOnline(true);
    project.setVcsStart(new Date());
    project.setVcsEnd(new Date());

    Mockito.when(createProjectPort.createProject(any())).thenReturn(1L);

    Long projectId = testSubject.createProject(command);

    Assertions.assertEquals(1L, projectId.longValue());
  }
}
