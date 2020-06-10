package io.reflectoring.coderadar.rest.unit.user;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.rest.useradministration.permissions.RemoveUserFromProjectController;
import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driver.permissions.RemoveUserFromProjectUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RemoveUserFromProjectControllerTest {
  private final RemoveUserFromProjectUseCase removeUserFromProjectUseCase =
      mock(RemoveUserFromProjectUseCase.class);
  private final RemoveUserFromProjectController testController =
      new RemoveUserFromProjectController(removeUserFromProjectUseCase);

  @Test
  public void testRemoveUserFromProjectController() {
    ResponseEntity<HttpStatus> response = testController.removeUserRoleFromProject(1L, 2L);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void testRemoveUserFromProjectControllerThrowsExceptionWhenUserNotFound() {
    Mockito.doThrow(UserNotFoundException.class)
        .when(removeUserFromProjectUseCase)
        .removeUserFromProject(1L, 2L);
    Assertions.assertThrows(
        UserNotFoundException.class, () -> testController.removeUserRoleFromProject(1L, 2L));
  }

  @Test
  public void testRemoveUserFromProjectControllerThrowsExceptionWhenProjectNotFound() {
    Mockito.doThrow(ProjectNotFoundException.class)
        .when(removeUserFromProjectUseCase)
        .removeUserFromProject(1L, 2L);
    Assertions.assertThrows(
        ProjectNotFoundException.class, () -> testController.removeUserRoleFromProject(1L, 2L));
  }
}
