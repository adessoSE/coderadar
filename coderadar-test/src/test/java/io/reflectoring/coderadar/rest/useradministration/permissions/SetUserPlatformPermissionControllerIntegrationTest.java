package io.reflectoring.coderadar.rest.useradministration.permissions;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.ErrorMessageResponse;
import io.reflectoring.coderadar.useradministration.service.security.PasswordUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

class SetUserPlatformPermissionControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private UserRepository userRepository;
  @Autowired private Session session;

  private UserEntity testUser;

  @BeforeEach
  void setUp() {
    testUser = new UserEntity();
    testUser.setUsername("username");
    testUser.setPassword(PasswordUtil.hash("password1"));
    testUser = userRepository.save(testUser);
  }

  @Test
  void setUserRoleForProjectSuccessfully() throws Exception {
    mvc()
        .perform(
            post("/api/users/" + testUser.getId() + "/admin?isAdmin=true")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(document("user/permission/set"))
        .andReturn();

    session.clear();
    Assertions.assertTrue(userRepository.findByUsername(testUser.getUsername()).isPlatformAdmin());
  }

  @Test
  void throwsExceptionWhenUserDoesNotExist() throws Exception {
    MvcResult result =
        mvc()
            .perform(
                post("/api/users/1000/admin?isAdmin=true").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andDo(document("user/permission/set"))
            .andReturn();

    String errorMessage =
        fromJson(result.getResponse().getContentAsString(), ErrorMessageResponse.class)
            .getErrorMessage();
    Assertions.assertEquals("User with id 1000 not found.", errorMessage);
  }
}
