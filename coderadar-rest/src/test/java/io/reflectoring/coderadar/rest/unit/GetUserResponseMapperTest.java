package io.reflectoring.coderadar.rest.unit;

import io.reflectoring.coderadar.rest.GetUserResponseMapper;
import io.reflectoring.coderadar.rest.domain.GetUserResponse;
import io.reflectoring.coderadar.useradministration.domain.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GetUserResponseMapperTest {
  @Test
  public void testUserResponseMapper() {
    List<User> users = new ArrayList<>();
    users.add(new User().setUsername("testUser1").setId(1L).setPassword("testPassword1"));
    users.add(new User().setUsername("testUser2").setId(2L).setPassword("testPassword2"));

    List<GetUserResponse> responses = GetUserResponseMapper.mapUsers(users);
    Assertions.assertEquals(2L, responses.size());

    Assertions.assertEquals("testUser1", responses.get(0).getUsername());
    Assertions.assertEquals(1L, responses.get(0).getId());
    Assertions.assertEquals("testUser2", responses.get(1).getUsername());
    Assertions.assertEquals(2L, responses.get(1).getId());
  }

  @Test
  public void testUserResponseSingleMapper() {
    User user = new User().setUsername("testUser1").setId(1L).setPassword("testPassword1");
    GetUserResponse response = GetUserResponseMapper.mapUser(user);
    Assertions.assertEquals("testUser1", response.getUsername());
    Assertions.assertEquals(1L, response.getId());
  }
}
