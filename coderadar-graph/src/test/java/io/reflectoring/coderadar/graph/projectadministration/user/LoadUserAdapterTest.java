package io.reflectoring.coderadar.graph.projectadministration.user;

import io.reflectoring.coderadar.graph.projectadministration.domain.UserEntity;
import io.reflectoring.coderadar.graph.projectadministration.user.repository.LoadUserRepository;
import io.reflectoring.coderadar.graph.projectadministration.user.service.LoadUserAdapter;
import io.reflectoring.coderadar.projectadministration.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;

@DisplayName("Load user")
public class LoadUserAdapterTest {
  private LoadUserRepository loadUserRepository = mock(LoadUserRepository.class);

  @Test
  @DisplayName("Should return user when passing valid argument")
  void shouldReturnUserWhenPassingValidArgument() {
    LoadUserAdapter loadUserAdapter = new LoadUserAdapter(loadUserRepository);

    Mockito.when(loadUserRepository.findById(anyLong())).thenReturn(Optional.of(new UserEntity()));
    User returnedUser = loadUserAdapter.loadUser(1L);
    Assertions.assertThat(returnedUser).isNotNull();
  }
}
