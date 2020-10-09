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
import io.reflectoring.coderadar.rest.domain.GetProjectResponse;
import java.util.Collections;
import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

class ListProjectsForTeamControllerIntegrationTest extends ControllerTestTemplate {

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
    testProject.setVcsUsername("testUser");
    projectRepository.save(testProject);

    teamEntity = new TeamEntity();
    teamEntity.setName("testTeam");
    teamEntity.setProjects(Collections.singletonList(testProject));
    teamRepository.save(teamEntity, 1);
  }

  @Test
  void listProjectsForTeamSuccessfully() throws Exception {
    MvcResult result =
        mvc()
            .perform(get("/api/teams/" + teamEntity.getId() + "/projects"))
            .andExpect(status().isOk())
            .andDo(document("teams/list/projects"))
            .andReturn();

    GetProjectResponse[] projects =
        fromJson(result.getResponse().getContentAsString(), GetProjectResponse[].class);
    Assertions.assertEquals(1L, projects.length);
    Assertions.assertEquals("project", projects[0].getName());
  }

  @Test
  void throwsExceptionWhenTeamDoesNotExist() throws Exception {
    MvcResult result =
        mvc().perform(get("/api/teams/1000/projects")).andExpect(status().isNotFound()).andReturn();

    String errorMessage =
        fromJson(result.getResponse().getContentAsString(), ErrorMessageResponse.class)
            .getErrorMessage();
    Assertions.assertEquals("Team with id 1000 not found.", errorMessage);
  }
}
