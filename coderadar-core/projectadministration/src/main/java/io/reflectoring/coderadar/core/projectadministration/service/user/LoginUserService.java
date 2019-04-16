package io.reflectoring.coderadar.core.projectadministration.service.user;

import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.LoginUserPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.LoginUserCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.LoginUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginUserService implements LoginUserUseCase {

    private final LoginUserPort port;

    @Autowired
    public LoginUserService(LoginUserPort port) {
        this.port = port;
    }

    @Override
    public User login(LoginUserCommand command) {
        User user = new User();
        user.setUsername(command.getUsername());
        user.setPassword(command.getPassword());
        user = port.login(user);
        return user;
    }
}
