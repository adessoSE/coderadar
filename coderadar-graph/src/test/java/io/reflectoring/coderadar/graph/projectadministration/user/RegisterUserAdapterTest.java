package io.reflectoring.coderadar.graph.projectadministration.user;

import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.graph.useradministration.service.RegisterUserAdapter;
import io.reflectoring.coderadar.useradministration.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

class RegisterUserAdapterTest {
  private UserRepository userRepository = mock(UserRepository.class);

  @Test
  @DisplayName("Should return long when passing valid argument")
  void shouldReturnLongWhenPassingValidArgument() {
    RegisterUserAdapter registerUserAdapter = new RegisterUserAdapter(userRepository);

    UserEntity testUser = new UserEntity();
    testUser.setId(20L);
    Mockito.when(userRepository.save(any())).thenReturn(testUser);

    Long returned = registerUserAdapter.register(new User());
    Assertions.assertThat(returned).isNotNull();
  }
}
