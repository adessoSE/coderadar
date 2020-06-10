package io.reflectoring.coderadar.rest.unit.user.teams;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.rest.domain.ErrorMessageResponse;
import io.reflectoring.coderadar.rest.useradministration.teams.RemoveTeamFromProjectController;
import io.reflectoring.coderadar.useradministration.TeamNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driver.teams.RemoveTeamFromProjectUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RemoveTeamFromProjectControllerTest {
  private final RemoveTeamFromProjectUseCase removeTeamFromProjectUseCase =
      mock(RemoveTeamFromProjectUseCase.class);
  private final RemoveTeamFromProjectController testController =
      new RemoveTeamFromProjectController(removeTeamFromProjectUseCase);

  @Test
  public void testRemoveTeamFromProject() {
    ResponseEntity<ErrorMessageResponse> response = testController.removeTeamFromProject(1L, 2L);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void testRemoveTeamFromProjectThrowsExceptionWhenTeamNotFound() {
    Mockito.doThrow(TeamNotFoundException.class)
        .when(removeTeamFromProjectUseCase)
        .removeTeam(1L, 2L);
    Assertions.assertThrows(
        TeamNotFoundException.class, () -> testController.removeTeamFromProject(1L, 2L));
  }

  @Test
  public void testRemoveTeamFromProjectThrowsExceptionWhenProjectNotFound() {
    Mockito.doThrow(ProjectNotFoundException.class)
        .when(removeTeamFromProjectUseCase)
        .removeTeam(1L, 2L);
    Assertions.assertThrows(
        ProjectNotFoundException.class, () -> testController.removeTeamFromProject(1L, 2L));
  }
}
