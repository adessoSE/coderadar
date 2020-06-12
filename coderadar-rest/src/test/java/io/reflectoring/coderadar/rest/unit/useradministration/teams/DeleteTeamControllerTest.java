package io.reflectoring.coderadar.rest.unit.useradministration.teams;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.rest.useradministration.teams.DeleteTeamController;
import io.reflectoring.coderadar.useradministration.TeamNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driver.teams.DeleteTeamUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class DeleteTeamControllerTest {
  private final DeleteTeamUseCase deleteTeamUseCase = mock(DeleteTeamUseCase.class);

  private final DeleteTeamController testController = new DeleteTeamController(deleteTeamUseCase);

  @Test
  public void testDeleteTeam() {
    ResponseEntity<HttpStatus> response = testController.deleteTeam(1L);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void testDeleteTeamThrowsExceptionIfTeamNotFound() {
    Mockito.doThrow(TeamNotFoundException.class).when(deleteTeamUseCase).deleteTeam(anyLong());
    Assertions.assertThrows(TeamNotFoundException.class, () -> testController.deleteTeam(1L));
  }
}
