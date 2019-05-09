package io.reflectoring.coderadar.graph.projectadministration.user;

import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.graph.projectadministration.user.repository.RegisterUserRepository;
import io.reflectoring.coderadar.graph.projectadministration.user.service.RegisterUserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class RegisterUserServiceTest {
    @Mock
    private RegisterUserRepository registerUserRepository;
    @InjectMocks
    RegisterUserService registerUserService;

    @Test
    @DisplayName("Should return long when passing valid argument")
    void shouldReturnLongWhenPassingValidArgument() {
        Long returned = registerUserService.register(new User());
        Assertions.assertThat(returned).isNotNull();
    }
}
