package io.reflectoring.coderadar.rest.useradministration.teams;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.graph.useradministration.domain.TeamEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.TeamRepository;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RemoveTeamFromProjectControllerIntegrationTest extends ControllerTestTemplate {

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
  void removeTeamFromProjectSuccessfully() throws Exception {
    mvc()
        .perform(delete("/api/projects/" + testProject.getId() + "/teams/" + teamEntity.getId()))
        .andExpect(status().isOk())
        .andDo(document("teams/remove/project"))
        .andReturn();

    List<TeamEntity> teams = teamRepository.listTeamsByProjectIdWithMembers(testProject.getId());
    Assertions.assertTrue(teams.isEmpty());
  }
}
