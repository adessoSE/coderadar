package io.reflectoring.coderadar.projectadministration.user;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.projectadministration.domain.User;
import io.reflectoring.coderadar.projectadministration.port.driven.user.LoadUserPort;
import io.reflectoring.coderadar.projectadministration.port.driver.user.load.LoadUserResponse;
import io.reflectoring.coderadar.projectadministration.service.user.load.LoadUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class LoadUserServiceTest {
  private LoadUserPort loadUserPort = mock(LoadUserPort.class);

  @Test
  void loadUserWithIdOne() {
    LoadUserService testSubject = new LoadUserService(loadUserPort);

    User user = new User();
    user.setId(1L);
    user.setUsername("username");

    Mockito.when(loadUserPort.loadUser(user.getId())).thenReturn(user);

    LoadUserResponse response = testSubject.loadUser(1L);

    Assertions.assertEquals(user.getUsername(), response.getUsername());
  }
}
