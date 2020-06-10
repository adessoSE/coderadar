package io.reflectoring.coderadar.rest.useradministration;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.GetProjectResponse;
import io.reflectoring.coderadar.useradministration.service.security.PasswordUtil;
import java.util.Collections;
import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class ListProjectsForUserControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private UserRepository userRepository;

  @Autowired private ProjectRepository projectRepository;

  private ProjectEntity testProject;
  private UserEntity testUser;

  @BeforeEach
  public void setUp() {
    projectRepository.deleteAll();
    testProject = new ProjectEntity();
    testProject.setVcsUrl("https://valid.url");
    testProject.setName("project");
    testProject.setVcsStart(new Date());
    testProject.setVcsOnline(true);
    testProject.setVcsPassword("testPassword");
    testProject.setVcsUsername("testUser");
    projectRepository.save(testProject);

    userRepository.deleteAll();
    testUser = new UserEntity();
    testUser.setUsername("username");
    testUser.setPassword(PasswordUtil.hash("password1"));
    testUser.setProjects(Collections.singletonList(testProject));
    userRepository.save(testUser);
  }

  @Test
  void listProjectsForUserSuccessfully() throws Exception {
    MvcResult result =
        mvc()
            .perform(get("/api/users/" + testUser.getId() + "/projects"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn(); // TODO: Document

    GetProjectResponse[] projects =
        fromJson(result.getResponse().getContentAsString(), GetProjectResponse[].class);
    Assertions.assertEquals(1, projects.length);
    Assertions.assertEquals("project", projects[0].getName());
    Assertions.assertEquals(testProject.getId(), projects[0].getId());
  }
}
