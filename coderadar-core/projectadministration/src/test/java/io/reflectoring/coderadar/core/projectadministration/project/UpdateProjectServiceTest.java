package io.reflectoring.coderadar.core.projectadministration.project;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.UpdateProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.update.UpdateProjectCommand;
import io.reflectoring.coderadar.core.projectadministration.service.project.UpdateProjectService;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class UpdateProjectServiceTest {
  @Mock private GetProjectPort getProjectPort;
  @Mock private UpdateProjectPort updateProjectPort;
  @InjectMocks private UpdateProjectService testSubject;

  @Test
  void updateProjectWithIdOne() throws MalformedURLException {
    URL url = new URL("http://valid.url");
    UpdateProjectCommand command =
        new UpdateProjectCommand(
            "new project name", "username", "password", url, true, new Date(), new Date());

    Project project = new Project();
    project.setId(1L);
    project.setName("new project name");

    Mockito.when(getProjectPort.get(1L)).thenReturn(Optional.of(project));

    testSubject.update(command, 1L);

    Mockito.verify(updateProjectPort, Mockito.times(1)).update(project);
  }
}
