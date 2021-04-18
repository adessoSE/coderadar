package io.reflectoring.coderadar.rest.unit.project;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driver.project.get.ListProjectsUseCase;
import io.reflectoring.coderadar.rest.domain.GetProjectResponse;
import io.reflectoring.coderadar.rest.project.ListProjectsController;
import io.reflectoring.coderadar.rest.unit.UnitTestTemplate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ListProjectsControllerTest extends UnitTestTemplate {

  private final ListProjectsUseCase listProjectsUseCase = mock(ListProjectsUseCase.class);

  @Test
  void listAllProjects() {
    ListProjectsController testSubject = new ListProjectsController(listProjectsUseCase);

    Project project1 = new Project();
    Project project2 = new Project();
    Project project3 = new Project();

    List<Project> projects = new ArrayList<>();
    projects.add(project1);
    projects.add(project2);
    projects.add(project3);

    Mockito.when(listProjectsUseCase.listProjects()).thenReturn(projects);

    ResponseEntity<List<GetProjectResponse>> responseEntity = testSubject.listProjects();
    Assertions.assertNotNull(responseEntity.getBody());
    Assertions.assertEquals(3L, responseEntity.getBody().size());
    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}
