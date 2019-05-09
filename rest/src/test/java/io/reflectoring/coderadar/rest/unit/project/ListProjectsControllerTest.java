package io.reflectoring.coderadar.rest.unit.project;

import io.reflectoring.coderadar.core.projectadministration.port.driver.project.get.GetProjectResponse;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.get.ListProjectsUseCase;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ListProjectsControllerTest {

  @Mock private ListProjectsUseCase listProjectsUseCase;
  @InjectMocks private ListProjectsController testSubject;

  @Test
  void listAllProjects() {
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
