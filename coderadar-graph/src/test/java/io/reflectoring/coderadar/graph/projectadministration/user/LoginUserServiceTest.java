package io.reflectoring.coderadar.graph.projectadministration.user;

import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.graph.projectadministration.user.repository.LoginUserRepository;
import io.reflectoring.coderadar.graph.projectadministration.user.service.LoginUserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("Login user")
public class LoginUserServiceTest {
    @Mock private LoginUserRepository loginUserRepository;
    @InjectMocks private LoginUserService loginUserService;

    @Test
    @DisplayName("Should return user when passing valid argument")
    void shouldReturnUserWhenPassingValidArgument() {
        User returnedUser = loginUserService.login(new User());
        Assertions.assertThat(returnedUser).isNotNull();
    }
}
