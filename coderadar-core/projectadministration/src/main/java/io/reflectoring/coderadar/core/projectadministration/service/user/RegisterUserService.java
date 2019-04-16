package io.reflectoring.coderadar.core.projectadministration.service.user;

import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.RegisterUserPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.RegisterUserCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.RegisterUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserService implements RegisterUserUseCase {

    private final RegisterUserPort port;

    @Autowired
    public RegisterUserService(RegisterUserPort port) {
        this.port = port;
    }

    @Override
    public Long register(RegisterUserCommand command) {
        User user = new User();
        user.setUsername(command.getUsername());
        user.setPassword(command.getPassword());
        return port.register(user);
    }
}
