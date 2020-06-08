package io.reflectoring.coderadar.rest.unit.user.teams;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.rest.domain.GetProjectResponse;
import io.reflectoring.coderadar.rest.useradministration.teams.ListProjectsForTeamController;
import io.reflectoring.coderadar.useradministration.TeamNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driver.teams.get.ListProjectsForTeamUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;

public class ListProjectsForTeamControllerTest {
  private final ListProjectsForTeamUseCase listProjectsForTeamUseCase =
      mock(ListProjectsForTeamUseCase.class);
  private final ListProjectsForTeamController testController =
      new ListProjectsForTeamController(listProjectsForTeamUseCase);

  @Test
  public void testListProjectsForTeam() {
    // Set up
    Mockito.when(listProjectsForTeamUseCase.listProjects(1L))
        .thenReturn(Collections.singletonList(new Project().setName("TestProject1").setId(2L)));

    ResponseEntity<List<GetProjectResponse>> response = testController.listProjectsForTeam(1L);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals(1L, response.getBody().size());
    Assertions.assertEquals(2L, response.getBody().get(0).getId());
    Assertions.assertEquals("TestProject1", response.getBody().get(0).getName());
  }

  @Test
  public void testRemoveTeamFromProjectThrowsExceptionWhenTeamNotFound() {
    Mockito.doThrow(TeamNotFoundException.class).when(listProjectsForTeamUseCase).listProjects(1L);
    Assertions.assertThrows(
        TeamNotFoundException.class, () -> testController.listProjectsForTeam(1L));
  }
}
