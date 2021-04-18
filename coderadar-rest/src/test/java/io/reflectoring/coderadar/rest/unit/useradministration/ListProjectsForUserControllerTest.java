package io.reflectoring.coderadar.rest.unit.useradministration;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.domain.Project;
import io.reflectoring.coderadar.domain.ProjectRole;
import io.reflectoring.coderadar.domain.ProjectWithRoles;
import io.reflectoring.coderadar.rest.domain.ProjectWithRolesResponse;
import io.reflectoring.coderadar.rest.unit.UnitTestTemplate;
import io.reflectoring.coderadar.rest.useradministration.ListProjectsForUserController;
import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driver.get.ListProjectsForUserUseCase;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ListProjectsForUserControllerTest extends UnitTestTemplate {
  private final ListProjectsForUserUseCase listProjectsForUserUseCase =
      mock(ListProjectsForUserUseCase.class);

  private final ListProjectsForUserController testController =
      new ListProjectsForUserController(listProjectsForUserUseCase);

  @Test
  void testListProjectsForUser() {
    // Set up
    Mockito.when(listProjectsForUserUseCase.listProjects(1L))
        .thenReturn(
            Collections.singletonList(
                new ProjectWithRoles()
                    .setProject(new Project().setName("TestProject1").setId(2L))
                    .setRoles(Collections.singletonList(ProjectRole.ADMIN))));

    ResponseEntity<List<ProjectWithRolesResponse>> response =
        testController.listProjectsForUser(1L);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals(1, response.getBody().size());
    Assertions.assertEquals(2L, response.getBody().get(0).getProject().getId());
    Assertions.assertEquals("TestProject1", response.getBody().get(0).getProject().getName());
    Assertions.assertEquals(ProjectRole.ADMIN, response.getBody().get(0).getRoles().get(0));
  }

  @Test
  void testListProjectsForUserThrowsWhenUserNotFound() {
    Mockito.when(listProjectsForUserUseCase.listProjects(1L))
        .thenThrow(new UserNotFoundException(1L));
    Assertions.assertThrows(
        UserNotFoundException.class, () -> testController.listProjectsForUser(1L));
  }
}
