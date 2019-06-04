package io.reflectoring.coderadar.core.projectadministration.project;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.ListProjectsPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.get.GetProjectResponse;
import io.reflectoring.coderadar.core.projectadministration.service.project.ListProjectsService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ListProjectsServiceTest {
  private ListProjectsPort port = mock(ListProjectsPort.class);

  @Test
  void returnsTwoProjects() {
    ListProjectsService testSubject = new ListProjectsService(port);

    Project project1 = new Project();
    project1.setId(1L);
    project1.setName("project 1");
    project1.setVcsUsername("username1");
    project1.setVcsPassword("password1");
    project1.setVcsOnline(true);
    project1.setVcsStart(new Date());
    project1.setVcsEnd(new Date());

    Project project2 = new Project();
    project2.setId(2L);
    project2.setName("project 2");
    project2.setVcsUsername("username2");
    project2.setVcsPassword("password2");
    project2.setVcsOnline(true);
    project2.setVcsStart(new Date());
    project2.setVcsEnd(new Date());
    List<Project> projects = new ArrayList<>();
    projects.add(project1);
    projects.add(project2);

    Mockito.when(port.getProjects()).thenReturn(projects);

    List<GetProjectResponse> response = testSubject.listProjects();

    Assertions.assertEquals(projects.size(), response.size());
    Assertions.assertEquals(project1.getId(), response.get(0).getId());
    Assertions.assertEquals(project1.getName(), response.get(0).getName());
    Assertions.assertEquals(project1.getVcsUsername(), response.get(0).getVcsUsername());
    Assertions.assertEquals(project1.getVcsPassword(), response.get(0).getVcsPassword());
    Assertions.assertEquals(project2.getId(), response.get(1).getId());
    Assertions.assertEquals(project2.getName(), response.get(1).getName());
    Assertions.assertEquals(project2.getVcsUsername(), response.get(1).getVcsUsername());
    Assertions.assertEquals(project2.getVcsPassword(), response.get(1).getVcsPassword());
  }
}
