package io.reflectoring.coderadar.rest.useradministration;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.ErrorMessageResponse;
import io.reflectoring.coderadar.rest.domain.ProjectWithRolesResponse;
import io.reflectoring.coderadar.useradministration.domain.ProjectRole;
import io.reflectoring.coderadar.useradministration.service.security.PasswordUtil;
import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

class ListProjectsForUserControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private UserRepository userRepository;

  @Autowired private ProjectRepository projectRepository;

  private ProjectEntity testProject;
  private UserEntity testUser;

  @BeforeEach
  void setUp() {
    testProject = new ProjectEntity();
    testProject.setVcsUrl("https://valid.url");
    testProject.setName("project");
    testProject.setVcsStart(new Date());
    testProject.setVcsPassword("testPassword");
    testProject.setVcsUsername("testUser");
    projectRepository.save(testProject);

    testUser = new UserEntity();
    testUser.setUsername("username");
    testUser.setPassword(PasswordUtil.hash("password1"));
    userRepository.save(testUser);

    userRepository.setUserRoleForProject(
        testProject.getId(), testUser.getId(), ProjectRole.ADMIN.toString());
  }

  @Test
  void listProjectsForUserSuccessfully() throws Exception {
    MvcResult result =
        mvc()
            .perform(get("/api/users/" + testUser.getId() + "/projects"))
            .andExpect(status().isOk())
            .andDo(document("user/list/projects"))
            .andReturn();

    ProjectWithRolesResponse[] projects =
        fromJson(result.getResponse().getContentAsString(), ProjectWithRolesResponse[].class);
    Assertions.assertEquals(1, projects.length);
    Assertions.assertEquals("project", projects[0].getProject().getName());
    Assertions.assertEquals(testProject.getId(), projects[0].getProject().getId());
  }

  @Test
  void throwsExceptionWhenUserDoesNotExist() throws Exception {
    MvcResult result =
        mvc().perform(get("/api/users/1000/projects")).andExpect(status().isNotFound()).andReturn();

    String errorMessage =
        fromJson(result.getResponse().getContentAsString(), ErrorMessageResponse.class)
            .getErrorMessage();
    Assertions.assertEquals("User with id 1000 not found.", errorMessage);
  }
}
