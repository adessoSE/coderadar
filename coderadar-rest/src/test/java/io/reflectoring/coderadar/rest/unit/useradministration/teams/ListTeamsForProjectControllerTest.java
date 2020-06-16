package io.reflectoring.coderadar.rest.unit.useradministration.teams;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.rest.domain.GetTeamResponse;
import io.reflectoring.coderadar.rest.unit.UnitTestTemplate;
import io.reflectoring.coderadar.rest.useradministration.teams.ListTeamsForProjectController;
import io.reflectoring.coderadar.useradministration.domain.Team;
import io.reflectoring.coderadar.useradministration.domain.User;
import io.reflectoring.coderadar.useradministration.port.driver.teams.get.ListTeamsForProjectUseCase;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ListTeamsForProjectControllerTest extends UnitTestTemplate {

  private final ListTeamsForProjectUseCase listTeamsForProjectUseCase =
      mock(ListTeamsForProjectUseCase.class);
  private final ListTeamsForProjectController testController =
      new ListTeamsForProjectController(listTeamsForProjectUseCase, authenticationService);

  @BeforeEach
  public void setUp() {
    Mockito.when(listTeamsForProjectUseCase.listTeamsForProject(5L))
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
    ResponseEntity<List<GetTeamResponse>> responseEntity = testController.listTeamsForProject(5L);
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

  @Test
  public void testListProjectsForUserThrowsWhenProjectNotFound() {
    Mockito.when(listTeamsForProjectUseCase.listTeamsForProject(8L))
        .thenThrow(new ProjectNotFoundException(1L));
    Assertions.assertThrows(
        ProjectNotFoundException.class, () -> testController.listTeamsForProject(8L));
  }
}
