package io.reflectoring.coderadar.rest.unit.useradministration.teams;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.domain.Team;
import io.reflectoring.coderadar.domain.User;
import io.reflectoring.coderadar.rest.domain.GetTeamResponse;
import io.reflectoring.coderadar.rest.unit.UnitTestTemplate;
import io.reflectoring.coderadar.rest.useradministration.teams.GetTeamController;
import io.reflectoring.coderadar.useradministration.TeamNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driver.teams.get.GetTeamUseCase;
import java.util.Collections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class GetTeamControllerTest extends UnitTestTemplate {
  private final GetTeamUseCase getTeamUseCase = mock(GetTeamUseCase.class);
  private final GetTeamController testController = new GetTeamController(getTeamUseCase);

  @Test
  void testGetTeam() {
    // Set up
    Mockito.when(getTeamUseCase.get(1L))
        .thenReturn(
            new Team()
                .setId(1L)
                .setName("TestTeam1")
                .setMembers(
                    Collections.singletonList(new User().setId(3L).setUsername("TestUser1"))));

    // Test
    ResponseEntity<GetTeamResponse> responseEntity = testController.getTeam(1L);
    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    Assertions.assertNotNull(responseEntity.getBody());
    Assertions.assertEquals(1L, responseEntity.getBody().getId());
    Assertions.assertEquals("TestTeam1", responseEntity.getBody().getName());

    Assertions.assertNotNull(responseEntity.getBody().getMembers());
    Assertions.assertEquals(1, responseEntity.getBody().getMembers().size());
    Assertions.assertEquals(3L, responseEntity.getBody().getMembers().get(0).getId());
    Assertions.assertEquals(
        "TestUser1", responseEntity.getBody().getMembers().get(0).getUsername());
  }

  @Test
  void testGetTeamReturnsErrorWhenTeamNotFound() {
    Mockito.when(getTeamUseCase.get(1L)).thenThrow(new TeamNotFoundException(1L));
    Assertions.assertThrows(TeamNotFoundException.class, () -> testController.getTeam(1L));
  }
}
