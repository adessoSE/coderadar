package io.reflectoring.coderadar.rest.unit.useradministration.permissions;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.rest.ProjectRoleJsonWrapper;
import io.reflectoring.coderadar.rest.unit.UnitTestTemplate;
import io.reflectoring.coderadar.rest.useradministration.permissions.SetUserRoleForProjectController;
import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.domain.ProjectRole;
import io.reflectoring.coderadar.useradministration.port.driver.permissions.SetUserRoleForProjectUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SetUserRoleForProjectControllerTest extends UnitTestTemplate {
  private final SetUserRoleForProjectUseCase setUserRoleForProjectUseCase =
      mock(SetUserRoleForProjectUseCase.class);
  private final SetUserRoleForProjectController testController =
      new SetUserRoleForProjectController(setUserRoleForProjectUseCase);

  @Test
  public void testSetUserRoleForProject() {
    ResponseEntity<HttpStatus> response =
        testController.setUserRoleForProject(1L, 2L, new ProjectRoleJsonWrapper(ProjectRole.ADMIN));
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void testSetUserRoleForProjectThrowsExceptionWhenUserNotFound() {
    Mockito.doThrow(UserNotFoundException.class)
        .when(setUserRoleForProjectUseCase)
        .setRole(1L, 2L, ProjectRole.ADMIN);
    Assertions.assertThrows(
        UserNotFoundException.class,
        () ->
            testController.setUserRoleForProject(
                1L, 2L, new ProjectRoleJsonWrapper(ProjectRole.ADMIN)));
  }

  @Test
  public void testSetUserRoleForProjectThrowsExceptionWhenProjectNotFound() {
    Mockito.doThrow(ProjectNotFoundException.class)
        .when(setUserRoleForProjectUseCase)
        .setRole(1L, 2L, ProjectRole.ADMIN);
    Assertions.assertThrows(
        ProjectNotFoundException.class,
        () ->
            testController.setUserRoleForProject(
                1L, 2L, new ProjectRoleJsonWrapper(ProjectRole.ADMIN)));
  }
}
