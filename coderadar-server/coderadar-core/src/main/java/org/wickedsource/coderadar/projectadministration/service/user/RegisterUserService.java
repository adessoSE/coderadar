package org.wickedsource.coderadar.projectadministration.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.projectadministration.port.driven.user.RegisterUserPort;
import org.wickedsource.coderadar.projectadministration.port.driver.user.RegisterUserUseCase;

@Service
public class RegisterUserService implements RegisterUserUseCase {

    private final RegisterUserPort port;

    @Autowired
    public RegisterUserService(RegisterUserPort port) {
        this.port = port;
    }
}
