package io.reflectoring.coderadar.rest.user;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static io.reflectoring.coderadar.rest.ResultMatchers.containsResource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.GetUserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class GetUserControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private UserRepository userRepository;

  @Test
  void loadUserWithId() throws Exception {
    UserEntity testUser = new UserEntity();
    testUser.setUsername("username2");
    testUser.setPassword("password1");
    testUser = userRepository.save(testUser);

    final Long userId = testUser.getId();

    mvc()
        .perform(get("/api/user/" + userId))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(containsResource(GetUserResponse.class))
        .andDo(
            result -> {
              String a = result.getResponse().getContentAsString();
              GetUserResponse response = fromJson(a, GetUserResponse.class);
              Assertions.assertEquals("username2", response.getUsername());
              Assertions.assertEquals(userId, response.getId());
            })
        .andDo(document("user/get"));
  }

  @Test
  void loadUserWithIdOneReturnsErrorWhenUserNotFound() throws Exception {
    mvc()
        .perform(get("/api/user/1"))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(
            MockMvcResultMatchers.jsonPath("errorMessage").value("User with id 1 not found."));
  }
}
