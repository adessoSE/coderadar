package io.reflectoring.coderadar.rest.useradministration;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.reflectoring.coderadar.domain.ProjectRole;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.ErrorMessageResponse;
import io.reflectoring.coderadar.useradministration.service.security.PasswordUtil;
import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

class DeleteUserControllerIntegrationTest extends ControllerTestTemplate {

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
    testProject.setVcsUsername("testUser");
    projectRepository.save(testProject);

    testUser = new UserEntity();
    testUser.setUsername("username");
    testUser.setPassword(PasswordUtil.hash("password1"));
    testUser = userRepository.save(testUser);
  }

  @Test
  void deleteUserSuccessfully() throws Exception {

    mvc()
        .perform(delete("/api/users/" + testUser.getId()))
        .andExpect(status().isOk())
        .andDo(document("user/delete"))
        .andReturn();

    Assertions.assertTrue(userRepository.findAll().isEmpty());
  }

  @Test
  void deleteUserAndProjectSuccessfully() throws Exception {
    userRepository.setUserRoleForProject(
        testProject.getId(), testUser.getId(), ProjectRole.ADMIN.getValue(), true);

    mvc().perform(delete("/api/users/" + testUser.getId())).andExpect(status().isOk()).andReturn();

    Assertions.assertTrue(userRepository.findAll().isEmpty());
    Assertions.assertTrue(projectRepository.findAll().isEmpty());
  }

  @Test
  void throwsExceptionWhenUserDoesNotExist() throws Exception {
    MvcResult result =
        mvc().perform(delete("/api/users/1000")).andExpect(status().isNotFound()).andReturn();

    String errorMessage =
        fromJson(result.getResponse().getContentAsString(), ErrorMessageResponse.class)
            .getErrorMessage();
    Assertions.assertEquals("User with id 1000 not found.", errorMessage);
  }
}
