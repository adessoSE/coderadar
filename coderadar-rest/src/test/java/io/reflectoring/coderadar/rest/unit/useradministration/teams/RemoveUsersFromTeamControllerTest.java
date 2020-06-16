package io.reflectoring.coderadar.rest.unit.useradministration.teams;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.rest.JsonListWrapper;
import io.reflectoring.coderadar.rest.unit.UnitTestTemplate;
import io.reflectoring.coderadar.rest.useradministration.teams.RemoveUsersFromTeamController;
import io.reflectoring.coderadar.useradministration.TeamNotFoundException;
import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driver.teams.RemoveUsersFromTeamUseCase;
import java.util.Collections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RemoveUsersFromTeamControllerTest extends UnitTestTemplate {
  private final RemoveUsersFromTeamUseCase removeUsersFromTeamUseCase =
      mock(RemoveUsersFromTeamUseCase.class);
  private final RemoveUsersFromTeamController testController =
      new RemoveUsersFromTeamController(removeUsersFromTeamUseCase);

  @Test
  public void testRemoveUsersFromTeam() {
    ResponseEntity<HttpStatus> response =
        testController.removeUsersFromTeam(
            1L, new JsonListWrapper<>(Collections.singletonList(2L)));
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void testRemoveTeamFromProjectThrowsExceptionWhenTeamNotFound() {
    Mockito.doThrow(TeamNotFoundException.class)
        .when(removeUsersFromTeamUseCase)
        .removeUsersFromTeam(anyLong(), anyList());
    Assertions.assertThrows(
        TeamNotFoundException.class,
        () ->
            testController.removeUsersFromTeam(
                1L, new JsonListWrapper<>(Collections.singletonList(2L))));
  }

  @Test
  public void testRemoveTeamFromProjectThrowsExceptionWhenUserNotFound() {
    Mockito.doThrow(UserNotFoundException.class)
        .when(removeUsersFromTeamUseCase)
        .removeUsersFromTeam(anyLong(), anyList());
    Assertions.assertThrows(
        UserNotFoundException.class,
        () ->
            testController.removeUsersFromTeam(
                1L, new JsonListWrapper<>(Collections.singletonList(2L))));
  }
}
