package io.reflectoring.coderadar.rest.useradministration.teams;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.graph.useradministration.domain.TeamEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.TeamRepository;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.ErrorMessageResponse;
import io.reflectoring.coderadar.rest.domain.GetTeamResponse;
import java.util.Collections;
import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

class ListTeamsForProjectControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private TeamRepository teamRepository;

  @Autowired private ProjectRepository projectRepository;

  private TeamEntity teamEntity;
  private ProjectEntity testProject;

  @BeforeEach
  void setUp() {
    testProject = new ProjectEntity();
    testProject.setVcsUrl("https://valid.url");
    testProject.setName("project");
    testProject.setVcsStart(new Date());
    testProject.setVcsOnline(true);
    testProject.setVcsPassword("testPassword");
    testProject.setVcsUsername("testUser");
    projectRepository.save(testProject);

    teamEntity = new TeamEntity();
    teamEntity.setName("testTeam");
    teamEntity.setProjects(Collections.singletonList(testProject));
    teamRepository.save(teamEntity, 1);
  }

  @Test
  void listTeamsForProjectSuccessfully() throws Exception {
    MvcResult result =
        mvc()
            .perform(get("/api/projects/" + testProject.getId() + "/teams"))
            .andExpect(status().isOk())
            .andDo(document("teams/list/project"))
            .andReturn();

    GetTeamResponse[] teams =
        fromJson(result.getResponse().getContentAsString(), GetTeamResponse[].class);
    Assertions.assertEquals(1L, teams.length);
    Assertions.assertEquals("testTeam", teams[0].getName());
  }

  @Test
  void throwsExceptionWhenProjectDoesNotExist() throws Exception {
    MvcResult result =
        mvc().perform(get("/api/projects/1000/teams")).andExpect(status().isNotFound()).andReturn();

    String errorMessage =
        fromJson(result.getResponse().getContentAsString(), ErrorMessageResponse.class)
            .getErrorMessage();
    Assertions.assertEquals("Project with id 1000 not found.", errorMessage);
  }
}
