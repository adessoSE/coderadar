package io.reflectoring.coderadar.rest.useradministration.teams;

import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.graph.useradministration.domain.TeamEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.TeamRepository;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.ProjectRoleJsonWrapper;
import io.reflectoring.coderadar.useradministration.domain.ProjectRole;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class AddTeamToProjectControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private TeamRepository teamRepository;

  @Autowired private ProjectRepository projectRepository;

  private TeamEntity teamEntity;
  private ProjectEntity testProject;

  @BeforeEach
  void setUp() {
    teamEntity = new TeamEntity();
    teamEntity.setName("testTeam");
    teamRepository.save(teamEntity, 1);

    projectRepository.deleteAll();
    testProject = new ProjectEntity();
    testProject.setVcsUrl("https://valid.url");
    testProject.setName("project");
    testProject.setVcsStart(new Date());
    testProject.setVcsOnline(true);
    testProject.setVcsPassword("testPassword");
    testProject.setVcsUsername("testUser");
    projectRepository.save(testProject);
  }

  @Test
  void addTeamToProjectSuccessfully() throws Exception {
    ConstrainedFields<ProjectRoleJsonWrapper> fields = fields(ProjectRoleJsonWrapper.class);

    mvc()
        .perform(
            post("/api/projects/" + testProject.getId() + "/teams/" + teamEntity.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new ProjectRoleJsonWrapper(ProjectRole.ADMIN))))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(
            document(
                "teams/add/project",
                requestFields(
                    fields.withPath("role").description("The role of the team in the project"))));

    List<TeamEntity> teams = teamRepository.listTeamsByProjectIdWithMembers(testProject.getId());
    Assertions.assertEquals(1L, teams.size());
    Assertions.assertEquals("testTeam", teams.get(0).getName());
  }
}
