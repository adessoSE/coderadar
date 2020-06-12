package io.reflectoring.coderadar.rest.useradministration;

import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.GetUserResponse;
import io.reflectoring.coderadar.useradministration.service.security.PasswordUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class ListUsersControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private UserRepository userRepository;

  private UserEntity testUser;

  @BeforeEach
  public void setUp() {
    userRepository.deleteAll();
    testUser = new UserEntity();
    testUser.setUsername("username");
    testUser.setPassword(PasswordUtil.hash("password1"));
    userRepository.save(testUser);
  }

  @Test
  void listUsersSuccessfully() throws Exception {
    MvcResult result =
        mvc()
            .perform(get("/api/users"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(document("users/list"))
            .andReturn();

    GetUserResponse[] users =
        fromJson(result.getResponse().getContentAsString(), GetUserResponse[].class);
    Assertions.assertEquals(1, users.length);
    Assertions.assertEquals("username", users[0].getUsername());
  }
}
