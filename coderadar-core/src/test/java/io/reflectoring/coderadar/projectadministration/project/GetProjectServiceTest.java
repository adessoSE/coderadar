package io.reflectoring.coderadar.projectadministration.project;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.project.get.GetProjectResponse;
import io.reflectoring.coderadar.projectadministration.service.project.GetProjectService;
import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetProjectServiceTest {

  @Mock private GetProjectPort getProjectPort;

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

    Mockito.when(getProjectPort.get(1L)).thenReturn(project);

    GetProjectResponse response = testSubject.get(1L);

    Assertions.assertEquals(project.getId(), response.getId());
    Assertions.assertEquals(project.getName(), response.getName());
    Assertions.assertEquals(project.isVcsOnline(), response.getVcsOnline());
    Assertions.assertEquals(project.getVcsUsername(), response.getVcsUsername());
    Assertions.assertEquals(project.getVcsPassword(), response.getVcsPassword());
  }
}
