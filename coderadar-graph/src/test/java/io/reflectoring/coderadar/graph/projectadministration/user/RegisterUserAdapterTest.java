package io.reflectoring.coderadar.graph.projectadministration.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.graph.projectadministration.domain.UserEntity;
import io.reflectoring.coderadar.graph.projectadministration.user.repository.RegisterUserRepository;
import io.reflectoring.coderadar.graph.projectadministration.user.service.RegisterUserAdapter;
import io.reflectoring.coderadar.projectadministration.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RegisterUserAdapterTest {
  private RegisterUserRepository registerUserRepository = mock(RegisterUserRepository.class);

  @Test
  @DisplayName("Should return long when passing valid argument")
  void shouldReturnLongWhenPassingValidArgument() {
    RegisterUserAdapter registerUserAdapter = new RegisterUserAdapter(registerUserRepository);

    UserEntity testUser = new UserEntity();
    testUser.setId(20L);
    Mockito.when(registerUserRepository.save(any())).thenReturn(testUser);

    Long returned = registerUserAdapter.register(new User());
    Assertions.assertThat(returned).isNotNull();
  }
}
