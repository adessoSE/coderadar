package io.reflectoring.coderadar.graph.projectadministration.user;

import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.graph.projectadministration.user.repository.LoadUserRepository;
import io.reflectoring.coderadar.graph.projectadministration.user.service.LoadUserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("Load user")
public class LoadUserServiceTest {
  @Mock private LoadUserRepository loadUserRepository;
  @InjectMocks private LoadUserService loadUserService;

  @Test
  @DisplayName("Should return user when passing valid argument")
  void shouldReturnUserWhenPassingValidArgument() {
    User returnedUser = loadUserService.loadUser(1L);
    Assertions.assertThat(returnedUser).isNotNull();
  }
}