package io.reflectoring.coderadar.graph.projectadministration.user;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.graph.projectadministration.user.repository.LoadUserRepository;
import io.reflectoring.coderadar.graph.projectadministration.user.service.LoadUserService;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@DisplayName("Load user")
public class LoadUserServiceTest {
  private LoadUserRepository loadUserRepository = mock(LoadUserRepository.class);

  @Test
  @DisplayName("Should return user when passing valid argument")
  void shouldReturnUserWhenPassingValidArgument() {
    LoadUserService loadUserService = new LoadUserService(loadUserRepository);

    Mockito.when(loadUserRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
    Optional<User> returnedUser = loadUserService.loadUser(1L);
    Assertions.assertThat(returnedUser.isPresent()).isTrue();
  }
}
