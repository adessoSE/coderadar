package io.reflectoring.coderadar.core.projectadministration.project;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.domain.VcsCoordinates;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.get.GetProjectResponse;
import io.reflectoring.coderadar.core.projectadministration.service.project.GetProjectService;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class GetProjectServiceTest {

  @Mock private GetProjectPort getProjectPort;
  @InjectMocks private GetProjectService testSubject;

  @Test
  void returnsGetProjectResponseWithIdOne() throws MalformedURLException {
    URL url = new URL("http://valid.url");

    Project project = new Project();
    project.setId(1L);
    project.setName("project name");
    project.setWorkdirName("workdir name");
    VcsCoordinates coordinates = new VcsCoordinates(url);
    coordinates.setOnline(true);
    coordinates.setUsername("username");
    coordinates.setPassword("password");
    project.setVcsCoordinates(coordinates);

    Mockito.when(getProjectPort.get(1L)).thenReturn(project);

    GetProjectResponse response = testSubject.get(1L);

    Assertions.assertEquals(project.getId(), response.getId());
    Assertions.assertEquals(project.getName(), response.getName());
    Assertions.assertEquals(project.getVcsCoordinates().isOnline(), response.getVcsOnline());
    Assertions.assertEquals(project.getVcsCoordinates().getUsername(), response.getVcsUsername());
    Assertions.assertEquals(project.getVcsCoordinates().getPassword(), response.getVcsPassword());
  }
}
