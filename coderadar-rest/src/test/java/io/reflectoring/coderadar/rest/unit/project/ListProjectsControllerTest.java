package io.reflectoring.coderadar.rest.unit.project;

import io.reflectoring.coderadar.projectadministration.port.driver.project.get.GetProjectResponse;
import io.reflectoring.coderadar.projectadministration.port.driver.project.get.ListProjectsUseCase;
import io.reflectoring.coderadar.rest.project.ListProjectsController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

class ListProjectsControllerTest {

  private ListProjectsUseCase listProjectsUseCase = mock(ListProjectsUseCase.class);

  @Test
  void listAllProjects() {
    ListProjectsController testSubject = new ListProjectsController(listProjectsUseCase);

    GetProjectResponse project1 = new GetProjectResponse();
    GetProjectResponse project2 = new GetProjectResponse();
    GetProjectResponse project3 = new GetProjectResponse();

    List<GetProjectResponse> projects = new ArrayList<>();
    projects.add(project1);
    projects.add(project2);
    projects.add(project3);

    Mockito.when(listProjectsUseCase.listProjects()).thenReturn(projects);

    ResponseEntity<List<GetProjectResponse>> responseEntity = testSubject.listProjects();

    Assertions.assertEquals(3L, responseEntity.getBody().size());
    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}
