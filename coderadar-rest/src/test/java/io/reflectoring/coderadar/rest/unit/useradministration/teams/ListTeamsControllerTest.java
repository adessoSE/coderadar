package io.reflectoring.coderadar.rest.unit.useradministration.teams;

import io.reflectoring.coderadar.rest.domain.GetTeamResponse;
import io.reflectoring.coderadar.rest.useradministration.teams.ListTeamsController;
import io.reflectoring.coderadar.useradministration.domain.Team;
import io.reflectoring.coderadar.useradministration.domain.User;
import io.reflectoring.coderadar.useradministration.port.driver.teams.get.ListTeamsUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;

public class ListTeamsControllerTest {
  private final ListTeamsUseCase listTeamsUseCase = mock(ListTeamsUseCase.class);
  private final ListTeamsController testController = new ListTeamsController(listTeamsUseCase);

  @BeforeEach
  public void setUp() {
    Mockito.when(listTeamsUseCase.listTeams())
        .thenReturn(
            Arrays.asList(
                new Team().setId(1L).setName("TestTeam1"),
                new Team()
                    .setId(2L)
                    .setName("TestTeam2")
                    .setMembers(
                        Collections.singletonList(new User().setId(3L).setUsername("TestUser1")))));
  }

  @Test
  public void testListTeams() {
    ResponseEntity<List<GetTeamResponse>> responseEntity = testController.listTeams();
    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    Assertions.assertNotNull(responseEntity.getBody());
    Assertions.assertEquals(2, responseEntity.getBody().size());
    Assertions.assertEquals(1L, responseEntity.getBody().get(0).getId());
    Assertions.assertEquals("TestTeam1", responseEntity.getBody().get(0).getName());

    Assertions.assertEquals(2L, responseEntity.getBody().get(1).getId());
    Assertions.assertEquals("TestTeam2", responseEntity.getBody().get(1).getName());

    Assertions.assertNotNull(responseEntity.getBody().get(1).getMembers());
    Assertions.assertEquals(1, responseEntity.getBody().get(1).getMembers().size());
    Assertions.assertEquals(3L, responseEntity.getBody().get(1).getMembers().get(0).getId());
    Assertions.assertEquals(
        "TestUser1", responseEntity.getBody().get(1).getMembers().get(0).getUsername());
  }
}
