package io.reflectoring.coderadar.graph.projectadministration.user;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.graph.useradministration.service.LoadUserAdapter;
import io.reflectoring.coderadar.useradministration.domain.User;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@DisplayName("Load user")
public class LoadUserAdapterTest {
  private UserRepository userRepository = mock(UserRepository.class);

  @Test
  @DisplayName("Should return user when passing valid argument")
  void shouldReturnUserWhenPassingValidArgument() {
    LoadUserAdapter loadUserAdapter = new LoadUserAdapter(userRepository);

    Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(new UserEntity()));
    User returnedUser = loadUserAdapter.loadUser(1L);
    Assertions.assertThat(returnedUser).isNotNull();
  }
}
