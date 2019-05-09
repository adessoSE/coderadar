package io.reflectoring.coderadar.core.projectadministration.project;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.domain.VcsCoordinates;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.ListProjectsPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.get.GetProjectResponse;
import io.reflectoring.coderadar.core.projectadministration.service.project.ListProjectsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
class ListProjectsServiceTest {
  @Mock private ListProjectsPort port;
  @InjectMocks private ListProjectsService testSubject;

  @Test
  void returnsTwoProjects() {
    VcsCoordinates vcsCoordinates1 = new VcsCoordinates();
    vcsCoordinates1.setUsername("username1");
    vcsCoordinates1.setPassword("password1");

    VcsCoordinates vcsCoordinates2 = new VcsCoordinates();
    vcsCoordinates2.setUsername("username2");
    vcsCoordinates2.setPassword("password2");

    Project project1 = new Project();
    project1.setId(1L);
    project1.setName("project 1");
    project1.setVcsCoordinates(vcsCoordinates1);
    Project project2 = new Project();
    project2.setId(2L);
    project2.setName("project 2");
    project2.setVcsCoordinates(vcsCoordinates2);
    List<Project> projects = new ArrayList<>();
    projects.add(project1);
    projects.add(project2);

    Mockito.when(port.getProjects()).thenReturn(projects);

    List<GetProjectResponse> response = testSubject.listProjects();

    Assertions.assertEquals(projects.size(), response.size());
    Assertions.assertEquals(project1.getId(), response.get(0).getId());
    Assertions.assertEquals(project1.getName(), response.get(0).getName());
    Assertions.assertEquals(
        project1.getVcsCoordinates().getUsername(), response.get(0).getVcsUsername());
    Assertions.assertEquals(
        project1.getVcsCoordinates().getPassword(), response.get(0).getVcsPassword());
    Assertions.assertEquals(project2.getId(), response.get(1).getId());
    Assertions.assertEquals(project2.getName(), response.get(1).getName());
    Assertions.assertEquals(
        project2.getVcsCoordinates().getUsername(), response.get(1).getVcsUsername());
    Assertions.assertEquals(
        project2.getVcsCoordinates().getPassword(), response.get(1).getVcsPassword());
  }
}
