package io.reflectoring.coderadar.rest.unit.useradministration.teams;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.rest.ProjectRoleJsonWrapper;
import io.reflectoring.coderadar.rest.unit.UnitTestTemplate;
import io.reflectoring.coderadar.rest.useradministration.teams.AddTeamToProjectController;
import io.reflectoring.coderadar.useradministration.TeamNotFoundException;
import io.reflectoring.coderadar.useradministration.domain.ProjectRole;
import io.reflectoring.coderadar.useradministration.port.driver.teams.AddTeamToProjectUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AddTeamToProjectControllerTest extends UnitTestTemplate {
  private final AddTeamToProjectUseCase addTeamToProjectUseCase =
      mock(AddTeamToProjectUseCase.class);
  private final AddTeamToProjectController testController =
      new AddTeamToProjectController(addTeamToProjectUseCase, authenticationService);

  @Test
  public void testAddTeamToProject() {
    ResponseEntity<HttpStatus> response =
        testController.addTeamToProject(1L, 2L, new ProjectRoleJsonWrapper(ProjectRole.ADMIN));
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void testRemoveTeamFromProjectThrowsExceptionWhenTeamNotFound() {
    Mockito.doThrow(TeamNotFoundException.class)
        .when(addTeamToProjectUseCase)
        .addTeamToProject(1L, 2L, ProjectRole.ADMIN);
    Assertions.assertThrows(
        TeamNotFoundException.class,
        () ->
            testController.addTeamToProject(1L, 2L, new ProjectRoleJsonWrapper(ProjectRole.ADMIN)));
  }

  @Test
  public void testRemoveTeamFromProjectThrowsExceptionWhenProjectNotFound() {
    Mockito.doThrow(ProjectNotFoundException.class)
        .when(addTeamToProjectUseCase)
        .addTeamToProject(1L, 2L, ProjectRole.ADMIN);
    Assertions.assertThrows(
        ProjectNotFoundException.class,
        () ->
            testController.addTeamToProject(1L, 2L, new ProjectRoleJsonWrapper(ProjectRole.ADMIN)));
  }
}
