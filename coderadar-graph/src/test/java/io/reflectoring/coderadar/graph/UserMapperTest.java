package io.reflectoring.coderadar.graph;

import io.reflectoring.coderadar.graph.useradministration.UserMapper;
import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import io.reflectoring.coderadar.useradministration.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserMapperTest {
  private final UserMapper userMapper = new UserMapper();

  @Test
  void testMapDomainObject() {
    User testUser = new User().setId(1L).setUsername("testUsername").setPassword("testPassword");

    UserEntity result = userMapper.mapDomainObject(testUser);
    Assertions.assertEquals("testUsername", result.getUsername());
    Assertions.assertEquals("testPassword", result.getPassword());
    Assertions.assertNull(result.getId());
  }

  @Test
  void testMapGraphObject() {
    UserEntity testUser =
        new UserEntity().setId(1L).setUsername("testUsername").setPassword("testPassword");

    User result = userMapper.mapGraphObject(testUser);
    Assertions.assertEquals("testUsername", result.getUsername());
    Assertions.assertEquals("testPassword", result.getPassword());
    Assertions.assertEquals(1L, result.getId());
  }
}
