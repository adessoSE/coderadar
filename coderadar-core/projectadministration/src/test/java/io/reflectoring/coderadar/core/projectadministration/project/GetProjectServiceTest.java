package io.reflectoring.coderadar.core.projectadministration.project;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.get.GetProjectResponse;
import io.reflectoring.coderadar.core.projectadministration.service.project.GetProjectService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.mock;

class GetProjectServiceTest {

  private GetProjectPort getProjectPort = mock(GetProjectPort.class);

  @Test
  void returnsGetProjectResponseWithIdOne() {
    GetProjectService testSubject = new GetProjectService(getProjectPort);

    Project project = new Project();
    project.setId(1L);
    project.setName("project name");
    project.setWorkdirName("workdir name");
    project.setVcsUrl("http://valid.url");
    project.setVcsUsername("username");
    project.setVcsPassword("password");
    project.setVcsOnline(true);
    project.setVcsStart(new Date());
    project.setVcsEnd(new Date());

    Mockito.when(getProjectPort.get(1L)).thenReturn(Optional.of(project));

    GetProjectResponse response = testSubject.get(1L);

    Assertions.assertEquals(project.getId(), response.getId());
    Assertions.assertEquals(project.getName(), response.getName());
    Assertions.assertEquals(project.isVcsOnline(), response.getVcsOnline());
    Assertions.assertEquals(project.getVcsUsername(), response.getVcsUsername());
    Assertions.assertEquals(project.getVcsPassword(), response.getVcsPassword());
  }
}
