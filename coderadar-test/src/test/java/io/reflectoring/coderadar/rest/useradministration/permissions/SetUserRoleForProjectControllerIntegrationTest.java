package io.reflectoring.coderadar.rest.useradministration.permissions;

import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.ProjectRoleJsonWrapper;
import io.reflectoring.coderadar.useradministration.domain.ProjectRole;
import io.reflectoring.coderadar.useradministration.service.security.PasswordUtil;
import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class SetUserRoleForProjectControllerIntegrationTest extends ControllerTestTemplate {

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
    testProject.setVcsOnline(true);
    testProject.setVcsPassword("testPassword");
    testProject.setVcsUsername("testUser");
    projectRepository.save(testProject);

    testUser = new UserEntity();
    testUser.setUsername("username");
    testUser.setPassword(PasswordUtil.hash("password1"));
    userRepository.save(testUser);
  }

  @Test
  void setUserRoleForProjectSuccessfully() throws Exception {

    ConstrainedFields<ProjectRoleJsonWrapper> fields = fields(ProjectRoleJsonWrapper.class);

    mvc()
        .perform(
            post("/api/projects/" + testProject.getId() + "/users/" + testUser.getId())
                .content(toJson(new ProjectRoleJsonWrapper(ProjectRole.ADMIN)))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(
            document(
                "user/role/project/set",
                requestFields(
                    fields
                        .withCustomPath("role")
                        .description(
                            "The role the user should have for the given project. Can be either ADMIN or MEMBER"))))
        .andReturn();

    Assertions.assertEquals(1, userRepository.listUsersForProject(testProject.getId()).size());
    Assertions.assertEquals(
        "username", userRepository.listUsersForProject(testProject.getId()).get(0).getUsername());
  }
}
