package io.reflectoring.coderadar.graph.projectadministration.user;

import io.reflectoring.coderadar.graph.useradministration.adapter.GetUserAdapter;
import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.useradministration.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;

@DisplayName("Load user")
public class GetUserAdapterTest {
  private UserRepository userRepository = mock(UserRepository.class);

  @Test
  @DisplayName("Should return user when passing valid argument")
  void shouldReturnUserWhenPassingValidArgument() {
    GetUserAdapter getUserAdapter = new GetUserAdapter(userRepository);

    Mockito.when(userRepository.findById(anyLong()))
        .thenReturn(Optional.of(new UserEntity().setId(1L)));
    User returnedUser = getUserAdapter.getUser(1L);
    Assertions.assertThat(returnedUser).isNotNull();
  }
}
